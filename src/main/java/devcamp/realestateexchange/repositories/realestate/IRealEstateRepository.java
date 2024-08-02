package devcamp.realestateexchange.repositories.realestate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.projections.RealEstateBasicProjection;
import devcamp.realestateexchange.repositories.realestate.RealEstateRepositoryCustom;
import org.springframework.stereotype.Repository;
@Repository
public interface IRealEstateRepository
                extends JpaRepository<RealEstate, Integer>, JpaSpecificationExecutor<RealEstate>, RealEstateRepositoryCustom {
        @Query("SELECT r FROM RealEstate r")
        Page<RealEstateBasicProjection> findAllBasicProjections(Pageable pageable);
}
