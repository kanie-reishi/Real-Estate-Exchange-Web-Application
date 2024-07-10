package devcamp.realestateexchange.services.realestate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.repositories.media.IPhotoRepository;
import devcamp.realestateexchange.repositories.realestate.IRealEstateRepository;
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
    @Autowired
    private IPhotoRepository photoRepository;
    public Page<RealEstateDto> getAllRealEstateDtos(Pageable pageable) {
        Page<RealEstateDto> realEstates = realEstateRepository.findAllDto(pageable);
        realEstates.forEach(this::loadPhotoUrls);
        return realEstates;
    }
    private void loadPhotoUrls(RealEstateDto dto) {
        List<String> photoUrls = photoRepository.findUrlsByRealEstateId(dto.getId());
        dto.setPhotoUrls(photoUrls);
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
    public void createRealEstate(RealEstateDto realEstateDto) {
        RealEstate realEstate = modelMapper.map(realEstateDto, RealEstate.class);
        realEstateRepository.save(realEstate);
        List<Photo> photos = realEstateDto.getPhotoUrls().stream().map(url -> 
            Photo.builder().url(url).realEstate(realEstate).build())
                .collect(Collectors.toList());
        photoRepository.saveAll(photos);
    }
    public RealEstateDto getRealEstateById(Integer id){
        RealEstateDto realEstateDto = realEstateRepository.getRealEstateById(id);
        loadPhotoUrls(realEstateDto);
        return realEstateDto;
    }
}
