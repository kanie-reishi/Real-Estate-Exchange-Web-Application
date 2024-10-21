package devcamp.realestateexchange.repositories.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.projections.RealEstateProjection;

public interface ICustomerRepository extends JpaRepository<Customer, Integer>{
    // Get all customer
    Page<Customer> findAll(Pageable pageable);
}
