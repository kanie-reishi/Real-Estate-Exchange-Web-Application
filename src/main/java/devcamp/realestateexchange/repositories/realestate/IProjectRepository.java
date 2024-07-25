package devcamp.realestateexchange.repositories.realestate;

import org.springframework.data.jpa.repository.JpaRepository;
import devcamp.realestateexchange.entity.realestate.Project;

public interface IProjectRepository extends JpaRepository<Project, Integer> {

}
