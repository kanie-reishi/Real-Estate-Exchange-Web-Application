package devcamp.realestateexchange.repositories.realestate;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import devcamp.realestateexchange.entity.realestate.DesignUnit;
import devcamp.realestateexchange.projections.DesignUnitProjection;

public interface IDesignUnitRepository extends JpaRepository<DesignUnit, Integer>, JpaSpecificationExecutor<DesignUnit> {
    // Get all design unit
    @Query("SELECT du FROM DesignUnit du")
    Page<DesignUnitProjection> findAllProjections(Pageable pageable);

    // Get all design unit by project id
    @Query("SELECT du FROM DesignUnit du WHERE du.project.id = :projectId")
    List<DesignUnitProjection> findDesignUnitByProjectId(@Param("projectId") Integer projectId);

    // Get design unit by id
    @Query("SELECT du FROM DesignUnit du WHERE du.id = :id")
    DesignUnitProjection getDesignUnitById(@Param("id") Integer id);
}
