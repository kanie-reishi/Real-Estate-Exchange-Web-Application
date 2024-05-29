package devcamp.realestateexchange.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "design_unit")
public class DesignUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotNull(message = "Name is required")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "projects")
    private String projects;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private AddressMap address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "phone2")
    private String phone2;
    @Column(name = "fax")
    private String fax;
    @Column(name = "email")
    private String email;
    @Column(name = "website")
    private String website;
    @Column(name = "note")
    private String note;
    public DesignUnit() {
    }
    public DesignUnit(String name, String description, String projects, AddressMap address, String phone, String phone2, String fax, String email, String website, String note) {
        this.name = name;
        this.description = description;
        this.projects = projects;
        this.address = address;
        this.phone = phone;
        this.phone2 = phone2;
        this.fax = fax;
        this.email = email;
        this.website = website;
        this.note = note;
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
    public String getProjects() {
        return projects;
    }
    public void setProjects(String projects) {
        this.projects = projects;
    }
    public AddressMap getAddress() {
        return address;
    }
    public void setAddress(AddressMap address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone2() {
        return phone2;
    }
    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
}
