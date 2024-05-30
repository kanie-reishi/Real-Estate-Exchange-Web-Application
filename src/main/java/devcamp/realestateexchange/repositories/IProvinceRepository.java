package devcamp.realestateexchange.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import devcamp.realestateexchange.entity.Province;
import devcamp.realestateexchange.dto.ProvinceDto;
@Repository
public interface IProvinceRepository extends JpaRepository<Province, Integer>{

    @Query("SELECT new devcamp.realestateexchange.dto.ProvinceDto(p.id, p.name) FROM Province p WHERE p.id = :id")
    ProvinceDto getProvinceDtoById(Integer id);
}
