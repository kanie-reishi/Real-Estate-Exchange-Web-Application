package devcamp.realestateexchange.repositories;



import org.springframework.data.domain.Pageable;
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
    @Query("SELECT new devcamp.realestateexchange.dto.RealEstateDto(r.id, r.title, r.province.id, r.district.id, r.price, r.acreage, r.dateCreate, r.photo) FROM RealEstate r")
    Page<RealEstateDto> findAllDto(Pageable pageable);
    @Query("SELECT new devcamp.realestateexchange.dto.RealEstateDto(r.id, r.title, r.province.id, r.district.id, r.price, r.acreage, r.dateCreate, r.photo) FROM RealEstate r ORDER BY r.viewNum DESC")
    Page<RealEstateDto> findAllDtoOrderByViewCount(Pageable pageable);
    @Query("SELECT new devcamp.realestateexchange.dto.RealEstateDto(r.id, r.title, r.province.id, r.district.id, r.price, r.acreage, r.dateCreate, r.photo) FROM RealEstate r WHERE r.province.id = ?1")
    Page<RealEstateDto> findAllDtoFilterByProvinceId(Pageable pageable, Integer provinceId);
    @Query("SELECT new devcamp.realestateexchange.dto.RealEstateDto(r.id, r.title, r.province.id, r.district.id, r.price, r.acreage, r.dateCreate, r.photo) FROM RealEstate r WHERE r.district.id = ?1")
    Page<RealEstateDto> findAllDtoFilterByDistrictId(Pageable pageable, Integer districtId);
    @Query("SELECT new devcamp.realestateexchange.dto.RealEstateDto(r.id, r.title, r.province.id, r.district.id, r.price, r.acreage, r.dateCreate, r.photo) FROM RealEstate r WHERE r.price >= ?1 AND r.price <= ?2")
    Page<RealEstateDto> findAllDtoFilterByMinPriceAndMaxPrice(Pageable pageable, Double minPrice, Double maxPrice);
    @Query("SELECT new devcamp.realestateexchange.dto.RealEstateDto(r.id, r.title, r.province.id, r.district.id, r.price, r.acreage, r.dateCreate, r.photo) FROM RealEstate r WHERE r.acreage >= ?1 AND r.acreage <= ?2")
    Page<RealEstateDto> findAllDtoFilterByAcreage(Pageable pageable, Double minAcreage, Double maxAcreage);
    @Query("SELECT new devcamp.realestateexchange.dto.RealEstateDto(r.id, r.title, r.province.id, r.district.id, r.price, r.acreage, r.dateCreate, r.photo) FROM RealEstate r WHERE r.bedroom = ?1")
    Page<RealEstateDto> findAllDtoFilterByNumberBedroom(Pageable pageable, Integer bedroom);
}
