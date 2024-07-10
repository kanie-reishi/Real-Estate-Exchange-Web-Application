package devcamp.realestateexchange.repositories.location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.dto.location.WardDto;
import devcamp.realestateexchange.entity.location.Ward;
public interface IWardRepository extends JpaRepository<Ward, Integer> {
    @Query("SELECT new devcamp.realestateexchange.dto.location.WardDto(w.id, w.name) FROM Ward w WHERE w.id = :id")
    WardDto getWardDtoById(Integer id);
    @Query("SELECT new devcamp.realestateexchange.dto.location.WardDto(w.id, w.name) FROM Ward w WHERE w.district.id = :districtId")
    Page<WardDto> findAllDtoByDistrictId(Pageable pageable, Integer districtId);
}
