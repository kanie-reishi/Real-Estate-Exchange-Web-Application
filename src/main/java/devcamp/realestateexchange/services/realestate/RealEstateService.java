package devcamp.realestateexchange.services.realestate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.location.AddressDto;
import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
import devcamp.realestateexchange.entity.location.District;
import devcamp.realestateexchange.entity.location.Province;
import devcamp.realestateexchange.entity.location.Street;
import devcamp.realestateexchange.entity.location.Ward;
import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.realestate.ApartDetail;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.projections.RealEstateBasicProjection;
import devcamp.realestateexchange.repositories.location.IDistrictRepository;
import devcamp.realestateexchange.repositories.location.IProvinceRepository;
import devcamp.realestateexchange.repositories.location.IStreetRepository;
import devcamp.realestateexchange.repositories.location.IWardRepository;
import devcamp.realestateexchange.repositories.realestate.IRealEstateRepository;
import devcamp.realestateexchange.repositories.user.ICustomerRepository;
import devcamp.realestateexchange.services.media.PhotoService;
import devcamp.realestateexchange.specification.RealEstateSpecification;
import devcamp.realestateexchange.specification.SearchCriteria;

@Service
public class RealEstateService {
    @Autowired
    private IRealEstateRepository realEstateRepository;
    @Autowired
    private IProvinceRepository provinceReposotory;
    @Autowired
    private IDistrictRepository districtRepository;
    @Autowired
    private IWardRepository wardRepository;
    @Autowired
    private IStreetRepository streetRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    PhotoService photoService;
    @Autowired
    private ModelMapper modelMapper;
    /*@PersistenceContext
    private EntityManager entityManager; */

    public Page<RealEstateDto> getAllRealEstateDtos(String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            Page<RealEstateBasicProjection> projections = realEstateRepository.findAllBasicProjections(pageable);
            return projections.map(this::convertBasicProjectionToDto);
        } /* else { 
            try {
                 // Get the full-text entity manager
                FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

                // Create a query builder
                QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder()
                        .forEntity(RealEstate.class)
                        .get();

                // Create a Lucene full-text query
                org.apache.lucene.search.Query query = queryBuilder
                        .keyword()
                        .onFields("title", "detail.description", "address") // Specify the fields to search in
                        .matching(searchTerm)
                        .createQuery();

                // Wrap the Lucene query in a javax.persistence.Query
                FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, RealEstate.class);

                jpaQuery.setProjection("id", "title", "type", "request", "price", "priceUnit", "acreage", "acreageUnit",
                        "bedroom", "verify", "realEstateCode", "createdAt", "customer", "province", "district");

                // Set pagination
                jpaQuery.setFirstResult((int) pageable.getOffset());
                jpaQuery.setMaxResults(pageable.getPageSize());

                // Execute the query and get the results
                List<Object[]> results = jpaQuery.getResultList();

                List<RealEstateDto> dtos = results.stream()
                        .map(result -> {
                            RealEstateDto dto = new RealEstateDto();
                            dto.setId((Integer) result[0]);
                            dto.setTitle((String) result[1]);
                            dto.setRequest((Integer) result[3]);
                            dto.setPrice((BigDecimal) result[4]);
                            dto.setPriceUnit((Integer) result[5]);
                            dto.setAcreage((Double) result[6]);
                            dto.setAcreageUnit((Integer) result[7]);
                            dto.setBedroom((Integer) result[8]);
                            dto.setVerify((Integer) result[9]);
                            dto.setRealEstateCode((String) result[10]);
                            if (result[11] instanceof java.sql.Date) {
                                dto.setCreatedAt(new java.util.Date(((java.sql.Date) result[11]).getTime()));
                            } else if (result[11] instanceof java.sql.Timestamp) {
                                dto.setCreatedAt(new java.util.Date(((java.sql.Timestamp) result[11]).getTime()));
                            }
                            Customer customer = (Customer) result[12];
                            if (customer != null) {
                                CustomerDto customerDto = new CustomerDto();
                                customerDto.setId(customer.getId());
                                customerDto.setFullName(customer.getFullName());
                                customerDto.setPhone(customer.getPhone());
                                dto.setCustomer(customerDto);
                            }
                            Province province = (Province) result[13];
                            if (province != null) {
                                ProvinceDto provinceDto = new ProvinceDto();
                                provinceDto.setId(province.getId());
                                provinceDto.setName(province.getName());
                                AddressDto addressDto = new AddressDto();
                                addressDto.setProvince(provinceDto);
                                dto.setAddress(addressDto);
                            }
                            District district = (District) result[14];
                            if (district != null) {
                                DistrictDto districtDto = new DistrictDto();
                                districtDto.setId(district.getId());
                                districtDto.setName(district.getName());
                                districtDto.setPrefix(district.getPrefix());
                                AddressDto addressDto = dto.getAddress();
                                addressDto.setDistrict(districtDto);
                                dto.setAddress(addressDto);
                            }

                            return dto;
                        })
                        .collect(Collectors.toList());
                // Get the total number of results
                jpaQuery = fullTextEntityManager.createFullTextQuery(query);
                int total = jpaQuery.getResultList().size();

                // Return the results as a Page
                return new PageImpl<>(dtos, pageable, total);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } 
        } */
        return null;
    }

    private RealEstateDto convertBasicProjectionToDto(RealEstateBasicProjection projection) {
        RealEstateDto dto = new RealEstateDto();
        dto.setId(projection.getId());
        dto.setTitle(projection.getTitle());
        dto.setType(projection.getType());
        dto.setRequest(projection.getRequest());
        dto.setRealEstateCode(projection.getRealEstateCode());
        dto.setPrice(projection.getPrice());
        dto.setPriceUnit(projection.getPriceUnit());
        dto.setAcreage(projection.getAcreage());
        dto.setAcreageUnit(projection.getAcreageUnit());
        dto.setBedroom(projection.getBedroom());
        dto.setVerify(projection.getVerify());
        dto.setCreatedAt(projection.getCreatedAt());

        CustomerDto customerDto = new CustomerDto();
        if (projection.getCustomer() != null) { // Check null
            customerDto.setId(projection.getCustomer().getId());
            customerDto.setFullName(projection.getCustomer().getFullName());
            customerDto.setPhone(projection.getCustomer().getPhone());
        }
        dto.setCustomer(customerDto);

        AddressDto addressDto = new AddressDto();
        ProvinceDto provinceDto = new ProvinceDto();
        DistrictDto districtDto = new DistrictDto();

        if (projection.getProvince() != null) { // Check null
            provinceDto.setId(projection.getProvince().getId());
            provinceDto.setName(projection.getProvince().getName());
            addressDto.setProvince(provinceDto);
        }
        addressDto.setProvince(provinceDto);

        if (projection.getDistrict() != null) { // Check null
            districtDto.setId(projection.getDistrict().getId());
            districtDto.setName(projection.getDistrict().getName());
            districtDto.setPrefix(projection.getDistrict().getPrefix());
        }
        addressDto.setDistrict(districtDto);

        dto.setAddress(addressDto);

        List<String> photoUrls = photoService.getUrlsByRealEstateId(dto.getId());
        dto.setPhotoUrls(photoUrls);
        return dto;
    }

    public Page<RealEstateDto> searchRealEstates(Integer provinceId, Integer districtId, Double minPrice,
            Double maxPrice, Double minAcreage, Double maxAcreage, Integer bedroom, String address, Pageable pageable) {
        List<Specification> specifications = new ArrayList<>();
        if (provinceId != null) {
            specifications.add(new RealEstateSpecification(new SearchCriteria("province.id", ":", provinceId)));
        }
        if (districtId != null) {
            specifications.add(new RealEstateSpecification(new SearchCriteria("district.id", ":", districtId)));
        }
        if (minPrice != null) {
            specifications.add(new RealEstateSpecification(new SearchCriteria("price", ">", minPrice)));
        }
        if (maxPrice != null) {
            specifications.add(new RealEstateSpecification(new SearchCriteria("price", "<", maxPrice)));
        }
        if (minAcreage != null) {
            specifications.add(new RealEstateSpecification(new SearchCriteria("acreage", ">", minAcreage)));
        }
        if (maxAcreage != null) {
            specifications.add(new RealEstateSpecification(new SearchCriteria("acreage", "<", maxAcreage)));
        }
        if (bedroom != null) {
            specifications.add(new RealEstateSpecification(new SearchCriteria("bedroom", ":", bedroom)));
        }
        if (address != null) {
            specifications.add(new RealEstateSpecification(new SearchCriteria("address", ":", address)));
        }

        Specification result = specifications.stream().reduce((spec1, spec2) -> spec1.and(spec2)).orElse(null);
        return realEstateRepository.findAll(result, pageable)
                .map(realEstate -> modelMapper.map(realEstate, RealEstateDto.class));
    }

    /*
     * public RealEstateDto getRealEstateDtoById(Integer id) {
     * RealEstateDto realEstateDto = realEstateRepository.findDtoById(id)
     * .orElseThrow(() -> new RuntimeException("RealEstate not found"));
     * List<String> photoUrls = photoService.getUrlsByRealEstateId(id);
     * realEstateDto.setPhotoUrls(photoUrls);
     * return realEstateDto;
     * }
     */
    public RealEstateDto saveRealEstate(RealEstateDto realEstateDto) {
        RealEstate realEstate = new RealEstate();
        realEstate.setTitle(realEstateDto.getTitle());
        realEstate.setType(realEstateDto.getType());
        realEstate.setRequest(realEstateDto.getRequest());
        realEstate.setPrice(realEstateDto.getPrice());
        realEstate.setPriceUnit(realEstateDto.getPriceUnit());
        realEstate.setAcreage(realEstateDto.getAcreage());
        realEstate.setAcreageUnit(realEstateDto.getAcreageUnit());
        realEstate.setBedroom(realEstateDto.getBedroom());
        realEstate.setCreatedAt(realEstateDto.getCreatedAt());
        realEstate.setDetail(realEstateDto.getDetail().toEntity());
        Customer customer = customerRepository.findById(realEstateDto.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        if (customer != null) {
            realEstate.setCustomer(customer);
        } else {
            throw new RuntimeException("Customer not found");
        }
        // Link sented photo of real estate
        List<Photo> photos = photoService.findByIds(realEstateDto.getPhotoIds());
        realEstate.setPhotos(photos);
        Province province = provinceReposotory.findById(realEstateDto.getAddress().getProvince().getId())
                .orElseThrow(() -> new RuntimeException("Province not found"));
        if (province != null) {
            realEstate.setProvince(province);
        } else {
            throw new RuntimeException("Province not found");
        }

        District district = districtRepository.findById(realEstateDto.getAddress().getDistrict().getId())
                .orElseThrow(() -> new RuntimeException("District not found"));
        if (district != null) {
            realEstate.setDistrict(district);
        } else {
            throw new RuntimeException("District not found");
        }

        Ward ward = wardRepository.findById(realEstateDto.getAddress().getWard().getId())
                .orElseThrow(() -> new RuntimeException("Ward not found"));
        if (ward != null) {
            realEstate.setWard(ward);
        } else {
            throw new RuntimeException("Ward not found");
        }

        Street street = streetRepository.findById(realEstateDto.getAddress().getStreet().getId())
                .orElseThrow(() -> new RuntimeException("Street not found"));
        if (street != null) {
            realEstate.setStreet(street);
        } else {
            throw new RuntimeException("Street not found");
        }

        if (realEstateDto.getType() == 2) {
            realEstate.setApartDetail(new ApartDetail(realEstateDto.getApartDetail().getApartCode(),
                    realEstateDto.getApartDetail().getApartLoca(), realEstateDto.getApartDetail().getApartType(),
                    realEstateDto.getApartDetail().getNumberFloors()));
        }
        realEstate = realEstateRepository.save(realEstate);

        return new RealEstateDto(realEstate);
    }
}
