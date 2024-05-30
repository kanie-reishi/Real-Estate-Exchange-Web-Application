package devcamp.realestateexchange.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import devcamp.realestateexchange.entity.District;
import devcamp.realestateexchange.dto.DistrictDto;
@Repository
public interface IDistrictRepository extends JpaRepository<District, Integer>{

    @Query("SELECT new devcamp.realestateexchange.dto.DistrictDto(d.id, d.name) FROM District d WHERE d.id = :id")
    DistrictDto getDistrictDtoById(Integer id);
}
