package devcamp.realestateexchange.services.location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.location.WardDto;
import devcamp.realestateexchange.entity.location.Ward;
import devcamp.realestateexchange.repositories.location.IWardRepository;
@Service
public class WardService {
    @Autowired
    private IWardRepository wardRepository;
    // Get ward by id
    public WardDto getWardById(Integer id){
        return wardRepository.getWardDtoById(id);
    }

    // Get all wards by district id
    public Page<WardDto> getWardListByDistrictId(Pageable pageable, Integer districtId){
        return wardRepository.findAllDtoByDistrictId(pageable, districtId);
    }
    // Get all wards
    public Page<WardDto> getWardList(Pageable pageable){
        return wardRepository.findAllDto(pageable);
    }

    // Delete ward by id
    public void deleteWardById(Integer id){
        wardRepository.deleteById(id);
    }

    // Save ward
    public Ward saveWard(Ward ward){
        return wardRepository.save(ward);
    }
}
