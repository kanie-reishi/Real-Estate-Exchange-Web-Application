package devcamp.realestateexchange.repositories.realestate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import devcamp.realestateexchange.entity.realestate.RegionLink;
import devcamp.realestateexchange.projections.RegionLinkProjection;
public interface IRegionLinkRepository extends JpaRepository<RegionLink, Integer>, JpaSpecificationExecutor<RegionLink> {
    // Get all region link by id
    @Query("SELECT rl FROM RegionLink rl")
    Page<RegionLinkProjection> findAllProjections(Pageable pageable);

    // Get all region link by project id
    @Query("SELECT rl FROM RegionLink rl WHERE rl.project.id = :projectId")
    List<RegionLinkProjection> findRegionLinkByProjectId(@Param("projectId") Integer projectId);

    // Get region link by id
    @Query("SELECT rl FROM RegionLink rl WHERE rl.id = :id")
    RegionLinkProjection getRegionLinkById(@Param("id") Integer id);
}
