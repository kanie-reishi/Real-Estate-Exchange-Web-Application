package devcamp.realestateexchange.services.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.entity.location.District;
import devcamp.realestateexchange.repositories.location.IDistrictRepository;
@Service
public class DistrictService {
    @Autowired
    private IDistrictRepository districtRepository;
    // Get all districts
    public Page<DistrictDto> getDistrictList(Pageable pageable){
        return districtRepository.findAllDto(pageable);
    }
    // Get district by id
    public DistrictDto getDistrictById(Integer id){
        return districtRepository.getDistrictDtoById(id);
    }

    // Get all districts by province id
    public Page<DistrictDto> getDistrictListByProvinceId(Pageable pageable, Integer provinceId){
        return districtRepository.findAllDtoByProvinceId(pageable, provinceId);
    }

    // Delete district by id
    public void deleteDistrictById(Integer id){
        districtRepository.deleteById(id);
    }

    // Save district
    public District saveDistrict(District district){
        return districtRepository.save(district);
    }

}
