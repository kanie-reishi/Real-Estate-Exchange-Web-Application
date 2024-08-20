package devcamp.realestateexchange.repositories.realestate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import devcamp.realestateexchange.entity.realestate.Investor;
import devcamp.realestateexchange.projections.UnitProjection;
public interface IInvestorRepository extends JpaRepository<Investor, Integer>, JpaSpecificationExecutor<Investor> {
    @Query("SELECT i FROM Investor i")
    Page<UnitProjection> findAllProjections(Pageable pageable);
    // Get all investors by project id
    @Query("SELECT i FROM Investor i WHERE i.project.id = :projectId")
    List<UnitProjection> findInvestorByProjectId(@Param("projectId") Integer projectId);
}
