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
    // Get employee by id
    public Employee getEmployeeById(Integer id) {
        return employeeRepository.getEmployeeById(id);
    }
    // Get all employees
    public Page<Employee> getEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }
    // Save employee
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    // Delete employee
    public void deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);
    }
}
