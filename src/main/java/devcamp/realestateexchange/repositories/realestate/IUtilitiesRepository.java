package devcamp.realestateexchange.repositories.realestate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.entity.realestate.Utilities;
import devcamp.realestateexchange.projections.UtilitiesProjection;
public interface IUtilitiesRepository extends JpaRepository<Utilities, Integer>, JpaSpecificationExecutor<Utilities> {
    @Query("SELECT u FROM Utilities u")
    Page<UtilitiesProjection> findAllProjections(Pageable pageable);
    // Get all utilities by project id
    @Query("SELECT u FROM Utilities u WHERE u.project.id = :projectId")
    UtilitiesProjection findUtilitiesByProjectId(Integer projectId);
}
