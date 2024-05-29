package devcamp.realestateexchange.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "master_layout")
public class MasterLayout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotNull(message = "Name is required")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @Column(name = "acreage")
    private double acreage;
    @Column(name = "price")
    private String apartmentList;
    @Column(name = "photo")
    private String photo;
    @Column(name = "date_create")
    private String dateCreate;
    @Column(name = "date_update")
    private String dateUpdate;
    public MasterLayout() {
    }
    public MasterLayout(int id, String name, String description, Project project, double acreage, String apartmentList, String photo, String dateCreate, String dateUpdate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.project = project;
        this.acreage = acreage;
        this.apartmentList = apartmentList;
        this.photo = photo;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
    public double getAcreage() {
        return acreage;
    }
    public void setAcreage(double acreage) {
        this.acreage = acreage;
    }
    public String getApartmentList() {
        return apartmentList;
    }
    public void setApartmentList(String apartmentList) {
        this.apartmentList = apartmentList;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public String getDateCreate() {
        return dateCreate;
    }
    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }
    public String getDateUpdate() {
        return dateUpdate;
    }
    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
    
}
