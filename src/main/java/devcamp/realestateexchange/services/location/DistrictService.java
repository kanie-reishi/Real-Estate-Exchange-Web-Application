package devcamp.realestateexchange.services.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.repositories.location.IDistrictRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
public class DistrictService {
    @Autowired
    private IDistrictRepository districtRepository;
    public DistrictDto getDistrictById(Integer id){
        return districtRepository.getDistrictDtoById(id);
    }
    public Page<DistrictDto> getDistrictListByProvinceId(Pageable pageable, Integer provinceId){
        return districtRepository.findAllDtoByProvinceId(pageable, provinceId);
    }
}
