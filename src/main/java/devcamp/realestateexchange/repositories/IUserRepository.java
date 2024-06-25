package devcamp.realestateexchange.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import devcamp.realestateexchange.entity.User;

public interface IUserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
  
    Boolean existsByEmail(String email);
}
