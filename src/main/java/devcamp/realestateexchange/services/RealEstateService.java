package devcamp.realestateexchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.RealEstateDto;
import devcamp.realestateexchange.repositories.IRealEstateRepository;
@Service
public class RealEstateService {
    @Autowired
    private IRealEstateRepository realEstateRepository;
    public Page<RealEstateDto> getRealEstateList(Pageable pageable){
        return realEstateRepository.findAllDto(pageable);
    }
}
