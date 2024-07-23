package devcamp.realestateexchange.repositories.realestate;

import java.util.Optional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.entity.realestate.RealEstate;

public interface IRealEstateRepository
                extends JpaRepository<RealEstate, Integer>, JpaSpecificationExecutor<RealEstate> {
        @Query("SELECT new devcamp.realestateexchange.dto.realestate.RealEstateDto("
                        + "re.id,"
                        + "re.title,"
                        + "re.type,"
                        + "re.request,"
                        + "re.price,"
                        + "re.priceUnit,"
                        + "re.acreage,"
                        + "re.acreageUnit,"
                        + "re.bedroom,"
                        + "re.verify,"
                        + "re.createdAt"
                        /* 
                        + " new devcamp.realestateexchange.dto.location.AddressDto("
                        + "re.address,"
                        + " new devcamp.realestateexchange.dto.location.ProvinceDto(re.province.id, re.province.name)," 
                        + " new devcamp.realestateexchange.dto.location.DistrictDto(re.district.id, re.district.name),"
                        + " new devcamp.realestateexchange.dto.location.WardDto(re.ward.id, re.ward.name)," 
                        + " new devcamp.realestateexchange.dto.location.StreetDto(re.street.id ,re.street.name),"
                        + "re.addressMap.latitude,"
                        + "re.addressMap.longitude)" */
                        + ")"
                        + " FROM RealEstate re")
        Page<RealEstateDto> findAllDtos(Pageable pageable);

        @Query("SELECT new devcamp.realestateexchange.dto.realestate.RealEstateDto("
                        + "re.id,"
                        + "re.title,"
                        + "re.type,"
                        + "re.request,"
                        + "re.price,"
                        + "re.priceUnit,"
                        + "re.acreage,"
                        + "re.acreageUnit,"
                        + "re.bedroom,"
                        + "re.verify,"
                        + "re.createdAt"
                        /* + " new devcamp.realestateexchange.dto.location.AddressDto("
                        + "re.address,"
                        + " new devcamp.realestateexchange.dto.location.ProvinceDto(re.province.id, re.province.name)," 
                        + " new devcamp.realestateexchange.dto.location.DistrictDto(re.district.id, re.district.name),"
                        + " new devcamp.realestateexchange.dto.location.WardDto(re.ward.id, re.ward.name)," 
                        + " new devcamp.realestateexchange.dto.location.StreetDto(re.street.id ,re.street.name),"
                        + "re.addressMap.latitude,"
                        + "re.addressMap.longitude)" */
                        + ")"
                        + " FROM RealEstate re"
                        + " WHERE re.id = :id")
        Optional<RealEstateDto> findDtoById(Integer id);


        @Query("SELECT re.id, re.title, re.type, re.request, re.price, re.priceUnit, re.acreage, re.acreageUnit, re.bedroom, re.verify, re.createdAt, re.address, re.province.id, re.province.name, re.district.id, re.district.name, re.ward.id, re.ward.name, re.street.id, re.street.name, re.addressMap.latitude, re.addressMap.longitude"
        + " FROM RealEstate re"
        + " ORDER BY re.article.viewNum DESC")
        List<Object[]> findAllByViewCountTest(Pageable pageable);
}
