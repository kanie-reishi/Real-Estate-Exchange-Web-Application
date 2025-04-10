package devcamp.realestateexchange.repositories.location;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.entity.location.Province;
public interface IProvinceRepository extends JpaRepository<Province, Integer>{
    // Get province by id
    @Query("SELECT new devcamp.realestateexchange.dto.location.ProvinceDto(p.id, p.name, p.code) FROM Province p WHERE p.id = :id")
    ProvinceDto getProvinceDtoById(Integer id);

    // Get all provinces
    @Query("SELECT new devcamp.realestateexchange.dto.location.ProvinceDto(p.id, p.name, p.code) FROM Province p")
    List<ProvinceDto> findAllDto();

    // Get all provinces order by name
    @Query("SELECT new devcamp.realestateexchange.dto.location.ProvinceDto(p.id, p.name, p.code) FROM Province p ORDER BY p.name")
    List<ProvinceDto> getProvinceListByName();
}
