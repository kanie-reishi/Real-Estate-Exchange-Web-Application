package devcamp.realestateexchange.repositories.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import devcamp.realestateexchange.entity.user.Customer;

public interface ICustomerRepository extends JpaRepository<Customer, Integer>{
    Page<Customer> findAll(Pageable pageable);
}
