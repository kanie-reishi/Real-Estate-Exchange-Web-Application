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

import devcamp.realestateexchange.controller.AuthController;
import devcamp.realestateexchange.security.services.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;

public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

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
        // 🔴 Check if token is blacklisted
        if (AuthController.isTokenBlacklisted(jwt)) {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has been revoked.");
          return;
        }
        // Get username from JWT token
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // Create authentication
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, // the authenticated user's details
            null, // don't need the password at this point
            userDetails.getAuthorities()); // the user's roles or permissions
        // Set authentication details
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // Set authentication in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (ExpiredJwtException e) {
      // Log error
      logger.error("Expired JWT token: {}", e.getMessage());
      // Token hết hạn
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token hết hạn. Vui lòng đăng nhập lại.");
      return;
    } catch (Exception e) {
      // Log error
      logger.error("Cannot set user authentication: {}", e);
      // Token không hợp lệ
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ.");
      return;
    }
    // Continue with the filter chain
    filterChain.doFilter(request, response);
  }

  public String parseJwtFromCookie(HttpServletRequest request) {
    // Check for JWT in cookies
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("token")) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
}
