package devcamp.realestateexchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.DistrictDto;
import devcamp.realestateexchange.entity.District;
import devcamp.realestateexchange.repositories.IDistrictRepository;

@Service
public class DistrictService {
    @Autowired
    private IDistrictRepository districtRepository;
    public DistrictDto getDistrictById(Integer id){
        return districtRepository.getDistrictDtoById(id);
    }
}
