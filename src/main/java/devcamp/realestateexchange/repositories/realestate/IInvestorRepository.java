package devcamp.realestateexchange.repositories.realestate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import devcamp.realestateexchange.entity.realestate.Investor;
import devcamp.realestateexchange.projections.InvestorProjection;

public interface IInvestorRepository extends JpaRepository<Investor, Integer>, JpaSpecificationExecutor<Investor> {
    // Get All Investors
    @Query("SELECT i FROM Investor i")
    Page<InvestorProjection> findAllProjections(Pageable pageable);

    // Get all investors by project id
    @Query("SELECT i FROM Investor i JOIN i.projectInvestors pi WHERE pi.project.id = :projectId")
    List<InvestorProjection> findInvestorByProjectId(@Param("projectId") Integer projectId);
    
    // Get investor by id
    @Query("SELECT i FROM Investor i WHERE i.id = :id")
    InvestorProjection getInvestorById(@Param("id") Integer id);
}
