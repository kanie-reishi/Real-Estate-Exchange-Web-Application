package devcamp.realestateexchange.repositories.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.dto.location.StreetDto;
import devcamp.realestateexchange.entity.location.Street;
public interface IStreetRepository extends JpaRepository<Street, Integer> {

    @Query("SELECT new devcamp.realestateexchange.dto.location.StreetDto(s.id, s.name) FROM Street s WHERE s.id = :id")
    StreetDto getStreetDtoById(Integer id);
    @Query("SELECT new devcamp.realestateexchange.dto.location.StreetDto(s.id, s.name) FROM Street s WHERE s.district.id = :districtId")
    Page<StreetDto> findAllDtoByDistrictId(Pageable pageable, Integer districtId);
}
