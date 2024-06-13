package devcamp.realestateexchange.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import devcamp.realestateexchange.entity.Photo;
import java.util.List;
@Repository
public interface IPhotoRepository extends JpaRepository<Photo, Integer>{
    Photo findByName(String name);
    @Query("SELECT p.url FROM Photo p WHERE p.realEstate.id = :realEstateId")
    List<String> findUrlsByRealEstateId(@Param("realEstateId") Integer realEstateId);
}
