package devcamp.realestateexchange.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.user.EmployeeDto;
import devcamp.realestateexchange.entity.user.Employee;
import devcamp.realestateexchange.services.user.EmployeeService;

@RestController
@CrossOrigin
@RequestMapping
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ModelMapper modelMapper;
    // constructor
    private EmployeeController(EmployeeService employeeService, ModelMapper modelMapper) {
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }
    // get all employees
    @GetMapping("/employees")
    public ResponseEntity<Object> getEmployeeList(Pageable pageable) {
        try {
            Page<Employee> employeePage = employeeService.getEmployees(pageable);
            return ResponseEntity.ok(employeePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // get Employee by id
    @GetMapping("/employees/{id}")
    public ResponseEntity<Object> getEmployeeById(@PathVariable Integer id) {
        try {
            Employee employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // save employee
    @PostMapping("/employees")
    public ResponseEntity<Object> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            Employee employee = modelMapper.map(employeeDto, Employee.class);
            employee = employeeService.saveEmployee(employee);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // delete employee by id
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Object> deleteEmployeeById(@PathVariable Integer id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
