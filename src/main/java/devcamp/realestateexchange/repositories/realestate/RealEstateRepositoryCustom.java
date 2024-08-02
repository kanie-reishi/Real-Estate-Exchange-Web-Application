package devcamp.realestateexchange.repositories.realestate;

import java.util.List;
import devcamp.realestateexchange.dto.realestate.RealEstateDto;

public interface RealEstateRepositoryCustom {
    List<RealEstateDto> search(String keyword);
}
