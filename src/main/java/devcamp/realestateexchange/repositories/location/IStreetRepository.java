package devcamp.realestateexchange.repositories.location;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import devcamp.realestateexchange.dto.location.StreetDto;
import devcamp.realestateexchange.entity.location.Street;

public interface IStreetRepository extends JpaRepository<Street, Integer> {
    // Get street by id
    @Query("SELECT new devcamp.realestateexchange.dto.location.StreetDto(s.id, s.name, s.prefix, s.province.id, s.district.id,s.province.name, s.district.name) FROM Street s WHERE s.id = :id")
    StreetDto getStreetDtoById(Integer id);

    // Get all streets by district id
    @Query("SELECT new devcamp.realestateexchange.dto.location.StreetDto(s.id, s.name, s.prefix, s.province.id, s.district.id, s.province.name, s.district.name) FROM Street s WHERE s.district.id = :districtId")
    Page<StreetDto> findAllDtoByDistrictId(Pageable pageable, Integer districtId);

    // Get all streets
    @Query("SELECT new devcamp.realestateexchange.dto.location.StreetDto(s.id, s.name, s.prefix, s.province.id, s.district.id, s.province.name, s.district.name) FROM Street s")
    Page<StreetDto> getStreetDtos(Pageable pageable);
}
