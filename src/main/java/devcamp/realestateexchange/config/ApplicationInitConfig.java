package devcamp.realestateexchange.config;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


import devcamp.realestateexchange.entity.authentication.ERole;
import devcamp.realestateexchange.entity.authentication.Role;
import devcamp.realestateexchange.entity.authentication.User;
import devcamp.realestateexchange.repositories.authentication.IRoleRepository;
import devcamp.realestateexchange.repositories.authentication.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";
    // Tạo một Bean ApplicationRunner
    // ApplicationRunner giúp chúng ta chạy một số logic khi ứng dụng được khởi động
    // Ở đây, chúng ta sẽ tạo một user admin mặc định nếu chưa có
    // Với username là admin và password là admin
    @Bean
    ApplicationRunner applicationRunner(IUserRepository userRepository, IRoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                roleRepository.save(Role.builder()
                        .roleName(ERole.ROLE_USER.name())
                        .roleKey("ROLE_USER")
                        .build());

                Role adminRole = roleRepository.save(Role.builder()
                        .roleName(ERole.ROLE_ADMIN.name())
                        .roleKey("ROLE_ADMIN")
                        .build());

                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                // Hãy nhớ thay đổi mật khẩu mặc định sau khi đăng nhập
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}