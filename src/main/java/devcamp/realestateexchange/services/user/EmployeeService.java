package devcamp.realestateexchange.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.entity.user.Employee;
import devcamp.realestateexchange.repositories.user.IEmployeeRepository;

@Service
public class EmployeeService {
    @Autowired
    private IEmployeeRepository employeeRepository;
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.getEmployeeById(id);
    }
    public Page<Employee> getEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
}
