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
    // Get all photo urls by real estate id
    @Query("SELECT p.url FROM Photo p WHERE p.realEstate.id = :realEstateId")
    List<String> findUrlsByRealEstateId(@Param("realEstateId") Integer realEstateId);
    // Get all photo urls by project id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.project.id = :projectId")
    List<PhotoUrlProjection> findPhotoUrlsByProjectId(@Param("projectId") Long projectId);
    // Get all photo urls by master layout id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.masterLayout.id = :masterLayoutId")
    List<PhotoUrlProjection> findPhotoUrlsByMasterLayoutId(@Param("masterLayoutId") Long masterLayoutId);

    // Get all photo urls by region link id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.regionLink.id = :regionLinkId")
    List<PhotoUrlProjection> findPhotoUrlsByRegionLinkId(@Param("regionLinkId") Long regionLinkId);

    // Get all photo urls by Utilities id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.utilities.id = :utilitiesId")
    List<PhotoUrlProjection> findPhotoUrlsByUtilitiesId(@Param("utilitiesId") Long utilitiesId);

    // Get all photo url by customer id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.customer.id = :customerId")
    PhotoUrlProjection findPhotoUrlByCustomerId(@Param("customerId") Long customerId);

    // Get all photo urls by employee id
    @Query("SELECT p.url AS url FROM Photo p WHERE p.employee.id = :employeeId")
    PhotoUrlProjection findPhotoUrlsByEmployeeId(@Param("employeeId") Long employeeId);
    
}
