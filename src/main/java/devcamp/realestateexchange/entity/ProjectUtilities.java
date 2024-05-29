package devcamp.realestateexchange.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GenerationType;
@Entity
public class ProjectUtilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "utilities_id")
    private Utilities utilities;
    public ProjectUtilities() {
    }
    public ProjectUtilities(int id, Project project, Utilities utilities) {
        this.id = id;
        this.project = project;
        this.utilities = utilities;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
    public Utilities getUtilities() {
        return utilities;
    }
    public void setUtilities(Utilities utilities) {
        this.utilities = utilities;
    }
    
}
