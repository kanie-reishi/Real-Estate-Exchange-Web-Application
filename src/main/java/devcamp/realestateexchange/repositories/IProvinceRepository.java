package devcamp.realestateexchange.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import devcamp.realestateexchange.entity.Province;
import devcamp.realestateexchange.dto.ProvinceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
@Repository
public interface IProvinceRepository extends JpaRepository<Province, Integer>{

    @Query("SELECT new devcamp.realestateexchange.dto.ProvinceDto(p.id, p.name) FROM Province p WHERE p.id = :id")
    ProvinceDto getProvinceDtoById(Integer id);
    @Query("SELECT new devcamp.realestateexchange.dto.ProvinceDto(p.id, p.name) FROM Province p")
    List<ProvinceDto> findAllDto();
}
