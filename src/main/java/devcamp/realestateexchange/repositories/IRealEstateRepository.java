package devcamp.realestateexchange.repositories;



import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import devcamp.realestateexchange.dto.RealEstateDto;
import devcamp.realestateexchange.entity.RealEstate;
@Repository
public interface IRealEstateRepository extends JpaRepository<RealEstate, Integer>, JpaSpecificationExecutor<RealEstate> {
    @Query("SELECT new devcamp.realestateexchange.dto.RealEstateDto(r.id, r.title, r.description, r.price, r.acreage, r.province.id, r.district.id, r.address, r.dateCreate) FROM RealEstate r")
    Page<RealEstateDto> findAllDto(Pageable pageable);
}
