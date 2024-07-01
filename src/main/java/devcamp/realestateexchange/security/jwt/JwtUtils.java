package devcamp.realestateexchange.security.jwt;

import java.security.SignatureException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import devcamp.realestateexchange.security.services.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${devcamp.app.jwtSecret}")
    private String jwtSecret;

    @Value("${devcamp.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // Generate JWT token
    public String generateJwtToken(Authentication authentication) {

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
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

        // Check if the token is expired
        return expiration.before(new Date());
    }
}