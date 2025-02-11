package devcamp.realestateexchange.services.location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.location.StreetDto;
import devcamp.realestateexchange.entity.location.Street;
import devcamp.realestateexchange.repositories.location.IStreetRepository;
@Service
public class StreetService {
    @Autowired
    private IStreetRepository streetRepository;

    // Get all streets
    public Page<StreetDto> getStreetList(Pageable pageable){
        return streetRepository.getStreetDtos(pageable);
    }
    // Get street by id
    public StreetDto getStreetById(Integer id){
        return streetRepository.getStreetDtoById(id);
    }

    // Get all streets by district id
    public Page<StreetDto> getStreetListByWardId(Pageable pageable, Integer districtId){
        return streetRepository.findAllDtoByDistrictId(pageable, districtId);
    }

    // Delete street by id
    public void deleteStreetById(Integer id){
        streetRepository.deleteById(id);
    }
    
    // Save street
    public Street saveStreet(Street street){
        return streetRepository.save(street);
    }
}
