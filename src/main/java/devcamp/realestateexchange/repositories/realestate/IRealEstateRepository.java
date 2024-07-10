package devcamp.realestateexchange.repositories.realestate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.entity.realestate.RealEstate;
public interface IRealEstateRepository extends JpaRepository<RealEstate, Integer>, JpaSpecificationExecutor<RealEstate> {
    @Query("SELECT new devcamp.realestateexchange.dto.realestate.RealEstateDto(r.id, r.title, r.description, r.price, r.acreage, r.bedroom, r.province.id, r.district.id, r.address, r.createdAt) FROM RealEstate r")
    Page<RealEstateDto> findAllDto(Pageable pageable);
    @Query("SELECT new devcamp.realestateexchange.dto.realestate.RealEstateDto(r.id, r.title, r.description, r.price, r.acreage, r.bedroom, r.province.id, r.district.id, r.address, r.createdAt) FROM RealEstate r WHERE r.id = :id")
    RealEstateDto getRealEstateById(Integer id);
}
