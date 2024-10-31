package devcamp.realestateexchange.services.location;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.entity.location.Province;
import devcamp.realestateexchange.repositories.location.IProvinceRepository;

@Service
public class ProvinceService {
    @Autowired
    // Get province by id
    private IProvinceRepository provinceRepository;

    public ProvinceDto getProvinceDtoById(Integer id) {
        return provinceRepository.getProvinceDtoById(id);
    }

    // Get all provinces
    public List<ProvinceDto> getProvinceList() {
        return provinceRepository.findAllDto();
    }

    // Delete province by id
    public void deleteProvinceById(Integer id) {
        provinceRepository.deleteById(id);
    }

    // Save province
    public Province saveProvince(Province province) {
        return provinceRepository.save(province);
    }
}
