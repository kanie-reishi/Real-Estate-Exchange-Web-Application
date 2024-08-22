package devcamp.realestateexchange.repositories.realestate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import devcamp.realestateexchange.entity.realestate.ConstructionContractor;
import devcamp.realestateexchange.projections.ContractorProjection;
import devcamp.realestateexchange.projections.UnitProjection;
public interface IConstructionContractorRepository extends JpaRepository<ConstructionContractor, Integer>, JpaSpecificationExecutor<ConstructionContractor> {
    @Query("SELECT cc FROM ConstructionContractor cc")
    Page<ContractorProjection> findAllProjections(Pageable pageable);

    // Get all construction contractor by project id
    @Query("SELECT cc FROM ConstructionContractor cc JOIN cc.projectConstructionContractors pcc WHERE pcc.project.id = :projectId")
    List<ContractorProjection> findConstructionContractorByProjectId(@Param("projectId") Integer projectId);

    @Query("SELECT cc FROM ConstructionContractor cc WHERE cc.id = :id")
    ContractorProjection getConstructionContractorById(@Param("id") Integer id);
}
