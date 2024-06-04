package devcamp.realestateexchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.ProvinceDto;
import devcamp.realestateexchange.entity.Province;
import devcamp.realestateexchange.repositories.IProvinceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
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
