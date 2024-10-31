package devcamp.realestateexchange.repositories.authentication;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import devcamp.realestateexchange.entity.authentication.User;

public interface IUserRepository extends JpaRepository<User, Integer>{
    // Find user by username
    Optional<User> findByUsername(String username);
    // Find user by email
    Boolean existsByUsername(String username);
    // Find user by phone
    Boolean existsByEmail(String email);
    // Find user by phone
    Boolean existsByPhone(String phone);
}
