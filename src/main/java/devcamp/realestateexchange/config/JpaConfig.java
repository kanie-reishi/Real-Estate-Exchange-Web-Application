package devcamp.realestateexchange.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import devcamp.realestateexchange.entity.authentication.User;
import devcamp.realestateexchange.security.services.UserDetailsImpl;

import org.slf4j.*;
// Tạo một Bean AuditorAware
// AuditorAware giúp chúng ta lấy thông tin về người tạo và người cập nhật một bản ghi
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaConfig {
    @Bean
    public AuditorAware<User> auditorProvider() {
        return new AuditorAwareImpl();
    }
    
    class AuditorAwareImpl implements AuditorAware<User> {
        @Override
        public Optional<User> getCurrentAuditor() {
            // Get the currently authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
                // If there's no authenticated user, return an empty Optional
                return Optional.empty();
            }
            // If there's an authenticated user, return an Optional that contains the User
            // object
            // Don't cast authentication.getPrincipal() to UserDetailsImpl directly, as it may not be the correct type
            // Instead, use the getPrincipal() method to get the User object
            // and cast it to UserDetailsImpl
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            // use Builder pattern to create a new User object
            User user = User.builder()
                    .username(userDetails.getUsername())
                    .email(userDetails.getEmail())
                    .phone(userDetails.getPhone())
                    .build();
            return Optional.of(user);
        }
    }
}