package devcamp.realestateexchange.services.location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.location.StreetDto;
import devcamp.realestateexchange.repositories.location.IStreetRepository;
@Service
public class StreetService {
    @Autowired
    private IStreetRepository streetRepository;
    public StreetDto getStreetById(Integer id){
        return streetRepository.getStreetDtoById(id);
    }
    public Page<StreetDto> getStreetListByWardId(Pageable pageable, Integer districtId){
        return streetRepository.findAllDtoByDistrictId(pageable, districtId);
    }
}
