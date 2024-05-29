package devcamp.realestateexchange.entity;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;
    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;
    @ManyToOne
    @JoinColumn(name = "street_id")
    private Street street;
    @Column(name = "address")
    private String address;
    @Column(name = "slogan")
    private String slogan;
    @Column(name = "description")
    private String description;
    @Column(name = "acreage")
    private double acreage;
    @Column(name = "construct_area")
    private double constuctArea;
    @Column(name = "num_block")
    private int numBlock;
    @Column(name = "num_floors")
    private String numFloors;
    @Column(name = "num_apartment")
    private int numApartment;
    @Column(name = "apartment_area")
    private String apartmentArea;
    @ManyToOne
    @JoinColumn(name = "investor", referencedColumnName = "id")
    private Investor investor;
    @ManyToOne
    @JoinColumn(name = "construction_contractor", referencedColumnName = "id")
    private ConstructionContractor constructionContractor;
    @ManyToOne
    @JoinColumn(name = "design_unit", referencedColumnName = "id")
    private DesignUnit designUnit;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<ProjectUtilities> projectUtilities;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<ProjectRegionLink> projectRegionLinks;
    @Column(name = "photo")
    private String photo;
    @Column(name = "lat")
    private double latitude;
    @Column(name = "lng")
    private double longitude;
    public Project() {
    }
    public Project(int id, String name, Province province, District district, Ward ward, Street street, String address,
            String slogan, String description, double acreage, double constuctArea, int numBlock, String numFloors,
            int numApartment, String apartmentArea, Investor investor, ConstructionContractor constructionContractor,
            DesignUnit designUnit, Set<ProjectUtilities> projectUtilities, Set<ProjectRegionLink> projectRegionLinks, String photo, double latitude,
            double longitude) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.street = street;
        this.address = address;
        this.slogan = slogan;
        this.description = description;
        this.acreage = acreage;
        this.constuctArea = constuctArea;
        this.numBlock = numBlock;
        this.numFloors = numFloors;
        this.numApartment = numApartment;
        this.apartmentArea = apartmentArea;
        this.investor = investor;
        this.constructionContractor = constructionContractor;
        this.designUnit = designUnit;
        this.projectUtilities = projectUtilities;
        this.projectRegionLinks = projectRegionLinks;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
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
    public Province getProvince() {
        return province;
    }
    public void setProvince(Province province) {
        this.province = province;
    }
    public District getDistrict() {
        return district;
    }
    public void setDistrict(District district) {
        this.district = district;
    }
    public Ward getWard() {
        return ward;
    }
    public void setWard(Ward ward) {
        this.ward = ward;
    }
    public Street getStreet() {
        return street;
    }
    public void setStreet(Street street) {
        this.street = street;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getSlogan() {
        return slogan;
    }
    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getAcreage() {
        return acreage;
    }
    public void setAcreage(double acreage) {
        this.acreage = acreage;
    }
    public double getConstuctArea() {
        return constuctArea;
    }
    public void setConstuctArea(double constuctArea) {
        this.constuctArea = constuctArea;
    }
    public int getNumBlock() {
        return numBlock;
    }
    public void setNumBlock(int numBlock) {
        this.numBlock = numBlock;
    }
    public String getNumFloors() {
        return numFloors;
    }
    public void setNumFloors(String numFloors) {
        this.numFloors = numFloors;
    }
    public int getNumApartment() {
        return numApartment;
    }
    public void setNumApartment(int numApartment) {
        this.numApartment = numApartment;
    }
    public String getApartmentArea() {
        return apartmentArea;
    }
    public void setApartmentArea(String apartmentArea) {
        this.apartmentArea = apartmentArea;
    }
    public Investor getInvestor() {
        return investor;
    }
    public void setInvestor(Investor investor) {
        this.investor = investor;
    }
    public ConstructionContractor getConstructionContractor() {
        return constructionContractor;
    }
    public void setConstructionContractor(ConstructionContractor constructionContractor) {
        this.constructionContractor = constructionContractor;
    }
    public DesignUnit getDesignUnit() {
        return designUnit;
    }
    public void setDesignUnit(DesignUnit designUnit) {
        this.designUnit = designUnit;
    }
    public Set<ProjectUtilities> getUtilities() {
        return projectUtilities;
    }
    public void setUtilities(Set<ProjectUtilities> projectUtilities) {
        this.projectUtilities = projectUtilities;
    }
    public Set<ProjectRegionLink> getRegionLinks() {
        return projectRegionLinks;
    }
    public void setRegionLinks(Set<ProjectRegionLink> projectRegionLinks) {
        this.projectRegionLinks = projectRegionLinks;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
}
