package devcamp.realestateexchange.repositories.authentication;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import devcamp.realestateexchange.entity.authentication.Role;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleKey(String roleKey);
}