package devcamp.realestateexchange.repositories.realestate;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import devcamp.realestateexchange.entity.realestate.MasterLayout;
import devcamp.realestateexchange.projections.MasterLayoutProjection;

public interface IMasterLayoutRepository extends JpaRepository<MasterLayout, Integer>, JpaSpecificationExecutor<MasterLayout> {
    // Get all master layout
    @Query("SELECT ml FROM MasterLayout ml")
    Page<MasterLayoutProjection> findAllProjections(Pageable pageable);

    // Get all master layout by project id
    @Query("SELECT ml FROM MasterLayout ml WHERE ml.project.id = :projectId")
    List<MasterLayoutProjection> findMasterLayoutByProjectId(@Param("projectId") Integer projectId);

    // Get master layout by id
    @Query("SELECT ml FROM MasterLayout ml WHERE ml.id = :id")
    MasterLayout getMasterLayoutById(@Param("id") Integer id);
}
