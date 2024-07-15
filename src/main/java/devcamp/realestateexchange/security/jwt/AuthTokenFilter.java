package devcamp.realestateexchange.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.data.redis.core.StringRedisTemplate;
import devcamp.realestateexchange.security.services.UserDetailsServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private StringRedisTemplate redisTemplate;
  // Logger
  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      // Get JWT token from request
      String jwt = parseJwtFromCookie(request);
      // Validate JWT token
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        // Check if token is in Redis
        if(!redisTemplate.hasKey(jwt)){
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token not found in Redis");
          return;
        }
        // Check if token is expired
        if(jwtUtils.isTokenExpired(jwt)){
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
          return;
        }
        // Get username from JWT token
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // Create authentication
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDetails, // the authenticated user's details
                null, // don't need the password at this point
                userDetails.getAuthorities()); // the user's roles or permissions
        // Set authentication details
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // Set authentication in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      // Log error
      logger.error("Cannot set user authentication: {}", e);
    }
    // Continue with the filter chain
    filterChain.doFilter(request, response);
  }

  public String parseJwtFromCookie(HttpServletRequest request) {
    // Get JWT token from cookie
    Cookie[] cookies = request.getCookies();
    // Check if cookies are not null
    if (cookies != null) {
      // Iterate through cookies
        for (Cookie cookie : cookies) {
          // Check if cookie name is "token"
            if ("token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
    }
    // Return null if no token is found
    return null;
  }
}
