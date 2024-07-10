package devcamp.realestateexchange.services.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.repositories.location.IProvinceRepository;
@Service
public class ProvinceService {
    @Autowired
    private IProvinceRepository provinceRepository;
    public ProvinceDto getProvinceDtoById(Integer id){
        return provinceRepository.getProvinceDtoById(id);
    }
    public List<ProvinceDto> getProvinceList(){
        return provinceRepository.findAllDto();
    }
}
