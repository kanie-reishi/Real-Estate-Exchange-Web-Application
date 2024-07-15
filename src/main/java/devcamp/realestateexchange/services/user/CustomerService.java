package devcamp.realestateexchange.services.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.repositories.user.ICustomerRepository;
@Service
public class CustomerService {
    @Autowired
    private ICustomerRepository customerRepository;

    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer updateCustomer(Integer id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer == null) {
            return null;
        }
        if(existingCustomer.getContactName() != null) {
            existingCustomer.setContactName(customer.getContactName());
        }
        if(existingCustomer.getPhone() != null) {
            existingCustomer.setPhone(customer.getPhone());
        }
        if(existingCustomer.getEmail() != null) {
            existingCustomer.setEmail(customer.getEmail());
        }
        if(existingCustomer.getAddress() != null) {
            existingCustomer.setAddress(customer.getAddress());
        }
        if(existingCustomer.getNote() != null) {
            existingCustomer.setNote(customer.getNote());
        }

        return customerRepository.save(existingCustomer);
    }
}
