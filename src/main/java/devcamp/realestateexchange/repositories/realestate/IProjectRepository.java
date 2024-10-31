package devcamp.realestateexchange.repositories.realestate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import devcamp.realestateexchange.entity.realestate.Project;
import devcamp.realestateexchange.projections.ProjectProjection;

public interface IProjectRepository extends JpaRepository<Project, Integer>, JpaSpecificationExecutor<Project>{
    @Query("SELECT p FROM Project p")
    Page<ProjectProjection> findAllProjections(Pageable pageable);
    @Query("SELECT p FROM Project p WHERE p.id = :id")
    ProjectProjection getProjectById(Integer id);
}
