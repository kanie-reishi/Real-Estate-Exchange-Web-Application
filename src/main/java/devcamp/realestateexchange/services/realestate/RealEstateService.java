package devcamp.realestateexchange.services.realestate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
import devcamp.realestateexchange.dto.location.AddressDto;
import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.entity.location.District;
import devcamp.realestateexchange.entity.location.Province;
import devcamp.realestateexchange.entity.location.Street;
import devcamp.realestateexchange.entity.location.Ward;
import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.realestate.ApartDetail;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.repositories.location.IDistrictRepository;
import devcamp.realestateexchange.repositories.location.IProvinceRepository;
import devcamp.realestateexchange.repositories.location.IStreetRepository;
import devcamp.realestateexchange.repositories.location.IWardRepository;
import devcamp.realestateexchange.repositories.realestate.IRealEstateRepository;
import devcamp.realestateexchange.repositories.user.ICustomerRepository;
import devcamp.realestateexchange.services.media.PhotoService;
import devcamp.realestateexchange.specification.RealEstateSpecification;
import devcamp.realestateexchange.specification.SearchCriteria;
import devcamp.realestateexchange.projections.RealEstateBasicProjection;

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
    public Page<RealEstateDto> getAllRealEstateDtos(Pageable pageable) {
    Page<RealEstateBasicProjection> projections = realEstateRepository.findAllBasicProjections(pageable);
    return projections.map(this::convertBasicProjectionToDto);
    }
    private RealEstateDto convertBasicProjectionToDto(RealEstateBasicProjection projection){
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
        customerDto.setId(projection.getCustomer().getId());
        customerDto.setFullName(projection.getCustomer().getFullName());
        customerDto.setPhone(projection.getCustomer().getPhone());
        dto.setCustomer(customerDto);

        AddressDto addressDto = new AddressDto();
        ProvinceDto provinceDto = new ProvinceDto();
        DistrictDto districtDto = new DistrictDto();

        provinceDto.setId(projection.getProvince().getId());
        provinceDto.setName(projection.getProvince().getName());
        addressDto.setProvince(provinceDto);
        districtDto.setId(projection.getDistrict().getId());
        districtDto.setName(projection.getDistrict().getName());
        districtDto.setPrefix(projection.getDistrict().getPrefix());
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
    /*public RealEstateDto getRealEstateDtoById(Integer id) {
       RealEstateDto realEstateDto = realEstateRepository.findDtoById(id)
                .orElseThrow(() -> new RuntimeException("RealEstate not found"));
        List<String> photoUrls = photoService.getUrlsByRealEstateId(id);
        realEstateDto.setPhotoUrls(photoUrls);
        return realEstateDto;
    }*/
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

        if(realEstateDto.getType() == 2){
            realEstate.setApartDetail(new ApartDetail(realEstateDto.getApartDetail().getApartCode(), realEstateDto.getApartDetail().getApartLoca(), realEstateDto.getApartDetail().getApartType(), realEstateDto.getApartDetail().getNumberFloors()));
        }
        realEstate = realEstateRepository.save(realEstate);

        return new RealEstateDto(realEstate);
    }

}
