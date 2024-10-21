package devcamp.realestateexchange.repositories.realestate;

import javax.persistence.Entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.projections.RealEstateProjection;
import devcamp.realestateexchange.projections.RealEstateExtendProjection;

@Repository
public interface IRealEstateRepository
                extends JpaRepository<RealEstate, Integer>, JpaSpecificationExecutor<RealEstate> {
        @Query("SELECT r FROM RealEstate r")
        Page<RealEstateProjection> findAllBasicProjections(Pageable pageable);

        // Get real estates by customer id
        @Query("SELECT r FROM RealEstate r WHERE r.customer.id = :customerId")
        Page<RealEstateProjection> findRealEstateByCustomerId(Integer customerId, Pageable pageable);

        // Get real estates by real estate id
        @Query("SELECT r FROM RealEstate r WHERE r.id = :realEstateId")
        RealEstateExtendProjection findRealEstateById(Integer realEstateId);
}
