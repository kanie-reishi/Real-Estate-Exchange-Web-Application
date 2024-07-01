package devcamp.realestateexchange.repositories;

import devcamp.realestateexchange.entity.Role;
import devcamp.realestateexchange.entity.ERole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleKey(String roleKey);
}
