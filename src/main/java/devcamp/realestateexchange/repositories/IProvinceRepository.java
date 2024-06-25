package devcamp.realestateexchange.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.dto.ProvinceDto;
import devcamp.realestateexchange.entity.Province;
public interface IProvinceRepository extends JpaRepository<Province, Integer>{

    @Query("SELECT new devcamp.realestateexchange.dto.ProvinceDto(p.id, p.name) FROM Province p WHERE p.id = :id")
    ProvinceDto getProvinceDtoById(Integer id);
    @Query("SELECT new devcamp.realestateexchange.dto.ProvinceDto(p.id, p.name) FROM Province p")
    List<ProvinceDto> findAllDto();
}
