package devcamp.realestateexchange.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.services.user.CustomerService;

@RestController
@CrossOrigin
@RequestMapping
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    // Get customer by id @param id @return CustomerDto
    @GetMapping("/customers/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable Integer id){
        try {
            CustomerDto customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Get all customers @return Page<CustomerDto>
    @GetMapping("/customers")
    public ResponseEntity<Object> getCustomers(Pageable pageable){
        try {
            Page<CustomerDto> customerPage = customerService.getAllCustomers(pageable);
            return ResponseEntity.ok(customerPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Save customer @param customerDto @return ResponseEntity<Object>
    @PostMapping("/customers")
    public ResponseEntity<Object> saveCustomer(@RequestBody CustomerDto customerDto){
        try {
            Customer customer = customerService.saveCustomer(customerDto);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Delete customer @param id
    @GetMapping("/customers/{customerId}/realEstates")
    public ResponseEntity<Object> getRealEstateListByCustomerId(Pageable pageable, @PathVariable Integer customerId){
        try {
            Page<RealEstateDto> realEstatePage = customerService.getRealEstateByCustomerId(pageable, customerId);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
