package devcamp.realestateexchange.repositories.media;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import devcamp.realestateexchange.entity.media.Video;
import devcamp.realestateexchange.projections.VideoProjection;

public interface IVideoRepository extends JpaRepository<Video, Integer>, JpaSpecificationExecutor<Video> {
    @Query("SELECT v FROM Video v")
    Page<VideoProjection> findAllProjections(Pageable pageable);
    // Get all video urls by real estate id
    @Query("SELECT v.url FROM Video v WHERE v.realEstate.id = :realEstateId")
    List<VideoProjection> findUrlsByRealEstateId(@Param("realEstateId") Integer realEstateId);
    // Get all video urls by project id
    @Query("SELECT v.url AS url FROM Video v WHERE v.project.id = :projectId")
    List<VideoProjection> findVideoUrlsByProjectId(@Param("projectId") Integer projectId);
    @Query("SELECT v FROM Video v WHERE v.id = :id")
    VideoProjection getVideoById(@Param("id") Integer id);
}
