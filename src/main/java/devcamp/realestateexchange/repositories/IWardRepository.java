package devcamp.realestateexchange.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import devcamp.realestateexchange.entity.Ward;
import devcamp.realestateexchange.dto.WardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Repository
public interface IWardRepository extends JpaRepository<Ward, Integer> {
    
    @Query("SELECT new devcamp.realestateexchange.dto.WardDto(w.id, w.name) FROM Ward w WHERE w.id = :id")
    WardDto getWardDtoById(Integer id);
    @Query("SELECT new devcamp.realestateexchange.dto.WardDto(w.id, w.name) FROM Ward w WHERE w.district.id = :districtId")
    Page<WardDto> findAllDtoByDistrictId(Pageable pageable, Integer districtId);
}
