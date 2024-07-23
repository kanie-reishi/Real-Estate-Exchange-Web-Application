package devcamp.realestateexchange.repositories.realestate;

import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.projections.RealEstateBasicProjection;

public interface IRealEstateRepository
                extends JpaRepository<RealEstate, Integer>, JpaSpecificationExecutor<RealEstate> {
        @Query("SELECT r FROM RealEstate r")
        Page<RealEstateBasicProjection> findAllBasicProjections(Pageable pageable);
}
