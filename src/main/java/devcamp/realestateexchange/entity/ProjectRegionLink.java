package devcamp.realestateexchange.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ProjectRegionLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @ManyToOne
    @JoinColumn(name = "region_link_id")
    private RegionLink regionLink;
    public ProjectRegionLink() {
    }
    public ProjectRegionLink(int id, Project project, RegionLink regionLink) {
        this.id = id;
        this.project = project;
        this.regionLink = regionLink;
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
    public RegionLink getRegionLink() {
        return regionLink;
    }
    public void setRegionLink(RegionLink regionLink) {
        this.regionLink = regionLink;
    }
}
