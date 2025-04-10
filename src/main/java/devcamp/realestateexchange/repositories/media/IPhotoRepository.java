package devcamp.realestateexchange.repositories.media;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.projections.PhotoUrlProjection;
public interface IPhotoRepository extends JpaRepository<Photo, Integer>, JpaSpecificationExecutor<Photo> {
    Photo findByName(String name);
    // Get all photo urls by project id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.project.id = :projectId")
    List<String> findPhotoUrlsByProjectId(@Param("projectId") Integer projectId);
    // Get all photo urls by master layout id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.masterLayout.id = :masterLayoutId")
    List<String> findPhotoUrlsByMasterLayoutId(@Param("masterLayoutId") Integer masterLayoutId);

    // Get all photo urls by region link id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.regionLink.id = :regionLinkId")
    List<String> findPhotoUrlsByRegionLinkId(@Param("regionLinkId") Integer regionLinkId);

    // Get all photo urls by Utilities id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.utilities.id = :utilitiesId")
    List<String> findPhotoUrlsByUtilitiesId(@Param("utilitiesId") Integer utilitiesId);

    // Get all photo url by customer id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.customer.id = :customerId")
    List<String> findPhotoUrlByCustomerId(@Param("customerId") Integer customerId);

    // Get all photo urls by employee id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.employee.id = :employeeId")
    List<String> findPhotoUrlsByEmployeeId(@Param("employeeId") Integer employeeId);
    
}
