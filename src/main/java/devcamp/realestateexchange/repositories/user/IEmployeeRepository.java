package devcamp.realestateexchange.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import devcamp.realestateexchange.entity.user.Employee;
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {

}
