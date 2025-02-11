package devcamp.realestateexchange.repositories.location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.entity.location.District;
public interface IDistrictRepository extends JpaRepository<District, Integer>{
    // Get district by id
    @Query("SELECT new devcamp.realestateexchange.dto.location.DistrictDto(d.id, d.name, d.prefix, d.province.id, d.province.name) FROM District d WHERE d.id = :id")
    DistrictDto getDistrictDtoById(Integer id);

    // Get all districts by province id
    @Query("SELECT new devcamp.realestateexchange.dto.location.DistrictDto(d.id, d.name, d.prefix, d.province.id, d.province.name) FROM District d WHERE d.province.id = :provinceId")
    Page<DistrictDto> findAllDtoByProvinceId(Pageable pageable, Integer provinceId);

    // Get all districts
    @Query("SELECT new devcamp.realestateexchange.dto.location.DistrictDto(d.id, d.name, d.prefix, d.province.id, d.province.name) FROM District d")
    Page<DistrictDto> findAllDto(Pageable pageable);
}
