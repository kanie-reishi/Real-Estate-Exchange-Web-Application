package devcamp.realestateexchange.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.RealEstateDto;
import devcamp.realestateexchange.repositories.IRealEstateRepository;
import devcamp.realestateexchange.specification.RealEstateSpecification;
import devcamp.realestateexchange.specification.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.modelmapper.ModelMapper;

@Service
public class RealEstateService {
    @Autowired
    private IRealEstateRepository realEstateRepository;
    @Autowired
    private ModelMapper modelMapper;
    public Page<RealEstateDto> getRealEstateList(Pageable pageable) {
        return realEstateRepository.findAllDto(pageable);
    }

    public Page<RealEstateDto> getRealEstateListOrderByViewCount(Pageable pageable) {
        return realEstateRepository.findAllDtoOrderByViewCount(pageable);
    }

    public Page<RealEstateDto> getRealEstateListFilterByProvinceId(Pageable pageable, Integer provinceId) {
        return realEstateRepository.findAllDtoFilterByProvinceId(pageable, provinceId);
    }

    public Page<RealEstateDto> getRealEstateListFilterByDistrictId(Pageable pageable, Integer districtId) {
        return realEstateRepository.findAllDtoFilterByDistrictId(pageable, districtId);
    }

    public Page<RealEstateDto> getRealEstateFilterByMinPriceAndMaxPrice(Pageable pageable, Double minPrice,
            Double maxPrice) {
        return realEstateRepository.findAllDtoFilterByMinPriceAndMaxPrice(pageable, minPrice, maxPrice);
    }

    public Page<RealEstateDto> getRealEstateFilterByAcreage(Pageable pageable, Double minAcreage, Double maxAcreage) {
        return realEstateRepository.findAllDtoFilterByAcreage(pageable, minAcreage, maxAcreage);
    }

    public Page<RealEstateDto> getRealEstateFilterByBedroom(Pageable pageable, Integer bedroom) {
        return realEstateRepository.findAllDtoFilterByNumberBedroom(pageable, bedroom);
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
}
