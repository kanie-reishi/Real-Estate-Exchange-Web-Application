package devcamp.realestateexchange.entity.realestate;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import devcamp.realestateexchange.entity.location.AddressMap;
import devcamp.realestateexchange.entity.location.District;
import devcamp.realestateexchange.entity.location.Province;
import devcamp.realestateexchange.entity.location.Street;
import devcamp.realestateexchange.entity.location.Ward;
import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.media.Video;
import devcamp.realestateexchange.entity.user.UserReferenceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project extends UserReferenceEntity{
    // Tên dự án
    @Column(name = "_name")
    private String name;

    // Quan hệ n-1 với Province
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    private Province province;

    // Quan hệ n-1 với District
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    // Quan hệ n-1 với Ward
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private Ward ward;

    // Quan hệ n-1 với Street
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "street_id")
    private Street street;

    // Phương châm của dự án
    @Column(name = "slogan")
    private String slogan;

    // Mô tả về dự án
    @Column(name = "description")
    private String description;

    // Địa chỉ dự án
    @Column(name = "address")
    private String address;

    // Diện tích tổng của dự án
    @Column(name = "acreage")
    private Double acreage;

    // Diện tích xây dựng
    @Column(name = "construct_area")
    private Double constructArea;

    // Số block dự án có
    @Column(name = "num_block")
    private Integer numBlock;

    // Số tầng, có thể là khoảng tương đối nên có kiểu text (20-38)
    @Column(name = "num_floors")
    private String numFloors;

    // Tổng số căn hộ.
    @Column(name = "num_apartment")
    private Integer numApartment;

    // Diện tích căn hộ, có thể là khoảng
    @Column(name = "apartment_area")
    private String apartmentArea;

    // Quan hệ 1-n với Photo
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Set<Photo> photos;

    // Quan hệ 1-n với Video
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Set<Video> videos;
    
    // Quan hệ 1-1 với AddressMap
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "addressMap_id", referencedColumnName = "id")
    private AddressMap addressMap;

    // Quan hệ n-n với Ultilities
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "project_utilities", 
        joinColumns = @JoinColumn(name = "project_id"), 
        inverseJoinColumns = @JoinColumn(name = "utilities_id")
    )
    private Set<Utilities> utilities;

    // Quan hệ n-n với RegionLink
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "project_region_link", 
        joinColumns = @JoinColumn(name = "project_id"), 
        inverseJoinColumns = @JoinColumn(name = "region_link_id")
    )
    private Set<RegionLink> regionLinks;
    
    // Quan hệ n-n với MasterLayout
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "project_master_layout", 
        joinColumns = @JoinColumn(name = "project_id"), 
        inverseJoinColumns = @JoinColumn(name = "master_layout_id")
    )
    private Set<MasterLayout> masterLayouts;

    // Quan hệ 1-n với RealEstate
    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<RealEstate> realEstates;

    // Quan hệ 1-n với ProjectConstructionContractor
    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<ProjectConstructionContractor> projectConstructionContractors;

    // Quan hệ 1-n với ProjectInvestor
    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<ProjectInvestor> projectInvestors;

    // Quan hệ 1-n với ProjectDesignUnit
    @OneToMany(mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<ProjectDesignUnit> projectDesignUnits;
}
