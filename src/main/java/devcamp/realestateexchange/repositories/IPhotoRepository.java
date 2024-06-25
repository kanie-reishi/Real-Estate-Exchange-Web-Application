package devcamp.realestateexchange.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import devcamp.realestateexchange.entity.Photo;
public interface IPhotoRepository extends JpaRepository<Photo, Integer>{
    Photo findByName(String name);
    @Query("SELECT p.url FROM Photo p WHERE p.realEstate.id = :realEstateId")
    List<String> findUrlsByRealEstateId(@Param("realEstateId") Integer realEstateId);
}
