package devcamp.realestateexchange.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import devcamp.realestateexchange.dto.WardDto;
import devcamp.realestateexchange.repositories.IWardRepository;
@Service
public class WardService {
    @Autowired
    private IWardRepository wardRepository;
    public WardDto getWardById(Integer id){
        return wardRepository.getWardDtoById(id);
    }
    public Page<WardDto> getWardListByDistrictId(Pageable pageable, Integer districtId){
        return wardRepository.findAllDtoByDistrictId(pageable, districtId);
    }
}
