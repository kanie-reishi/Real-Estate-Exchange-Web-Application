package devcamp.realestateexchange.services.user;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.projections.RealEstateProjection;
import devcamp.realestateexchange.repositories.realestate.IRealEstateRepository;
import devcamp.realestateexchange.repositories.user.ICustomerRepository;
import devcamp.realestateexchange.services.realestate.RealEstateService;
@Service
public class CustomerService {
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RealEstateService realEstateService;
    @Autowired
    private IRealEstateRepository realEstateRepository;
    // Get customer by id method
    public CustomerDto getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            return null;
        }
        return modelMapper.map(customer, CustomerDto.class);
    }
    // Save customer method @param customerDto
    public Customer saveCustomer(CustomerDto customer) {
        return customerRepository.save(modelMapper.map(customer, Customer.class));
    }
    // Save customer method @param customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    // Delete customer method @param id
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
    // Get all customers method
    public Page<CustomerDto> getAllCustomers(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(customer -> modelMapper.map(customer, CustomerDto.class));
    }
    // Update customer method @param id, customer
    public Customer updateCustomer(Integer id, Customer customer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);
        if (existingCustomer == null) {
            return null;
        }
        if(existingCustomer.getFullName() != null) {
            existingCustomer.setFullName(customer.getFullName());
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
    // Convert to dto method @param customer
    public CustomerDto convertToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }
    // Convert to entity method @param customerDto
    public Customer convertToEntity(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);
    }
    // Get real estate by customer id method @param pageable, customerId
    public Page<RealEstateDto> getRealEstateByCustomerId(Pageable pageable, Integer customerId) {
        List<RealEstateProjection> realEstateProjections = realEstateRepository.findRealEstateByCustomerId(customerId, pageable).getContent();
        List<RealEstateDto> realEstateDtos = new ArrayList<>();
        for(RealEstateProjection realEstateProjection : realEstateProjections) {
            RealEstateDto realEstateDto = realEstateService.convertProjectionToDto(realEstateProjection);
            realEstateDtos.add(realEstateDto);
        }
        Page<RealEstateDto> page = new PageImpl<>(realEstateDtos, pageable, realEstateDtos.size());
        return page;
    }

    // Get real estate by customer id method @param pageable, customerId, provinceId, type, areaRange, priceRange, bedroomRange
    public Page<RealEstateDto> getRealEstateByCustomerId(Pageable pageable, Integer customerId, Integer provinceId, Integer type, String areaRange, String priceRange, Integer bedroom) {
        // handle areaRange, priceRange, bedroomRange
        String[] areaRangeArray = areaRange.split("-");
        // if area contain +
        if(areaRangeArray[0].contains("+")) {
            areaRangeArray[0] = areaRangeArray[0].replace("+", "");
            areaRangeArray[1] = "9999999999";
        }
        Integer minArea = Integer.parseInt(areaRangeArray[0]);
        Integer maxArea = Integer.parseInt(areaRangeArray[1]);
        String[] priceRangeArray = priceRange.split("-");
        // if price contain +
        if(priceRangeArray[0].contains("+")) {
            priceRangeArray[0] = priceRangeArray[0].replace("+", "");
            priceRangeArray[1] = "9999999999";
        }
        Integer minPrice = Integer.parseInt(priceRangeArray[0]);
        Integer maxPrice = Integer.parseInt(priceRangeArray[1]);
        List<RealEstateProjection> realEstateProjections = realEstateRepository.findRealEstateByCustomerIdAndProvinceIdAndTypeAndAreaRangeAndPriceRangeAndBedroomRange(pageable, customerId, provinceId, type, minArea, maxArea, minPrice, maxPrice, bedroom).getContent();
        List<RealEstateDto> realEstateDtos = new ArrayList<>();
        for(RealEstateProjection realEstateProjection : realEstateProjections) {
            RealEstateDto realEstateDto = realEstateService.convertProjectionToDto(realEstateProjection);
            realEstateDtos.add(realEstateDto);
        }
        Page<RealEstateDto> page = new PageImpl<>(realEstateDtos, pageable, realEstateDtos.size());
        return page;
    }
}
