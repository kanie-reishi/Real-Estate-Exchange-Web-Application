package devcamp.realestateexchange.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import devcamp.realestateexchange.entity.authentication.User;
import org.slf4j.*;

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
            User user = (User) authentication.getPrincipal();
            return Optional.of(user);
        }
    }
}