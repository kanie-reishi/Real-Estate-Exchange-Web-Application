package devcamp.realestateexchange.security.jwt;

import java.security.SignatureException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import devcamp.realestateexchange.security.services.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

        @Value("${devcamp.app.jwtSecret}")
        private String jwtSecret;

        @Value("${devcamp.app.jwtExpirationMs}")
        private int jwtExpirationMs;

        long refreshExpirationDate = 86400000L;
        // Generate JWT token
        public String generateJwtToken(Authentication authentication) {

            UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userPrincipal.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return Jwts.builder()
                    .setSubject((userPrincipal.getUsername()))
                    .claim("scopes", roles)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
        }

        // Generate refresh token
        public String generateRefreshToken(Authentication authentication) {
            return Jwts.builder()
                    .setSubject((authentication.getName()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + refreshExpirationDate))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
        }
    // Get username from JWT token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate JWT token
    public boolean validateJwtToken(String authToken) throws SignatureException {
        try {
            // Jwts is a class that provides static methods to create and verify JWTs
            // parser() returns a new instance of the JwtParser
            // setSigningKey() sets the key that will be used to verify the signature of the
            // JWT
            // parseClaimsJws() parses the specified compact serialized JWS string and
            // returns the resulting Claims JWS
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
    // Validate refresh token
    public boolean validateRefreshToken(String refreshToken) throws SignatureException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(refreshToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
    public Date getExpirationDateFromToken(String token) {
        // Extract the claims from the token
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        // Return the expiration date
        return claims.getExpiration();
    }

    public boolean isTokenExpired(String token) {
        // Get the expiration date from the token
        Date expiration = getExpirationDateFromToken(token);
        // Get the expiration date from redis
        if (expiration == null) {
            return false;
        }
        // Check if the token is expired and mismatched with the expiration date
        long expirationTime = expiration.getTime();
        long currentTime = System.currentTimeMillis();
        return expirationTime < currentTime;
    }

    public String getJwtFromCookie(HttpServletRequest request) {
        // Get the cookies from the request
        Cookie[] cookies = request.getCookies();

        // If there are no cookies, return null
        if (cookies == null) {
            return null;
        }

        // Loop through the cookies
        for (Cookie cookie : cookies) {
            // If the cookie name is "token", return the cookie value
            if (cookie.getName().equals("token")) {
                return cookie.getValue();
            }
        }

        // If no cookie with the name "token" is found, return null
        return null;
    }
}
