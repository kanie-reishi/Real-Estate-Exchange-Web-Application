package devcamp.realestateexchange.repositories.realestate;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import devcamp.realestateexchange.entity.realestate.RealEstate;
public interface IRealEstateRepository extends JpaRepository<RealEstate, Integer>, JpaSpecificationExecutor<RealEstate> {
    Page<RealEstate> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Optional<RealEstate> findById(Integer id);
}
