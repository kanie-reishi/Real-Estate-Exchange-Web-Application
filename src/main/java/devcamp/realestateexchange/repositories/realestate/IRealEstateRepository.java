package devcamp.realestateexchange.repositories.realestate;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.entity.realestate.RealEstate;
public interface IRealEstateRepository extends JpaRepository<RealEstate, Integer>, JpaSpecificationExecutor<RealEstate> {
    @Query("SELECT new devcamp.realestateexchange.dto.realestate.RealEstateDto(r.id, r.title, r.price, r.acreage, r.bedroom, r.address, r.createdAt, r.updatedAt, r.province.name, r.district.name, r.user.username, r.user.id) FROM RealEstate r")
    Page<RealEstateDto> findAllDto(Pageable pageable);
    @Query("SELECT new devcamp.realestateexchange.dto.realestate.RealEstateDto(r.id, r.title, r.price, r.acreage, r.bedroom, r.address, r.createdAt, r.updatedAt, r.province.name, r.district.name, r.user.username, r.user.id) FROM RealEstate r WHERE r.id = ?1")
    Optional<RealEstate> findById(Integer id);
}
