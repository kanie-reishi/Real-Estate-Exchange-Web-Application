package devcamp.realestateexchange.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import devcamp.realestateexchange.dto.StreetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import devcamp.realestateexchange.entity.Street;
@Repository
public interface IStreetRepository extends JpaRepository<Street, Integer> {

    @Query("SELECT new devcamp.realestateexchange.dto.StreetDto(s.id, s.name) FROM Street s WHERE s.id = :id")
    StreetDto getStreetDtoById(Integer id);
    @Query("SELECT new devcamp.realestateexchange.dto.StreetDto(s.id, s.name) FROM Street s WHERE s.district.id = :districtId")
    Page<StreetDto> findAllDtoByDistrictId(Pageable pageable, Integer districtId);
}
