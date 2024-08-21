package devcamp.realestateexchange.repositories.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import devcamp.realestateexchange.entity.user.Employee;
public interface IEmployeeRepository extends JpaRepository<Employee, Integer> {
    Page<Employee> findAll(Pageable pageable);
    Employee getEmployeeById(Integer id);
}
