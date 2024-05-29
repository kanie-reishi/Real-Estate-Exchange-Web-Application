package devcamp.realestateexchange.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@Table(name = "province")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "_name")
    private String name;
    @Column(name = "_code")
    private String code;
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<District> districts;
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Ward> wards;
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Street> streets;
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Project> projects;
    public Province() {
    }
    public Province(String name, String code, Set<District> districts, Set<Ward> wards, Set<Street> streets) {
        this.name = name;
        this.code = code;
        this.districts = districts;
        this.wards = wards;
        this.streets = streets;
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
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public Set<District> getDistricts() {
        return districts;
    }
    public void setDistricts(Set<District> districts) {
        this.districts = districts;
    }
    public Set<Ward> getWards() {
        return wards;
    }
    public void setWards(Set<Ward> wards) {
        this.wards = wards;
    }
    public Set<Street> getStreets() {
        return streets;
    }
    public void setStreets(Set<Street> streets) {
        this.streets = streets;
    }
    
}
