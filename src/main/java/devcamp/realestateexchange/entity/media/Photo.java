package devcamp.realestateexchange.entity.media;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import devcamp.realestateexchange.entity.BaseEntity;
import devcamp.realestateexchange.entity.realestate.MasterLayout;
import devcamp.realestateexchange.entity.realestate.Project;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.entity.realestate.RegionLink;
import devcamp.realestateexchange.entity.realestate.Utilities;
import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.entity.user.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "photo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo extends BaseEntity {
    // Tên ảnh
    @Column(name = "name")
    private String name;
    
    // Url ảnh
    @Column(name = "url")
    @NotEmpty(message = "Photo url is required")
    private String url;

    // Loại ảnh
    @Column(name = "type")
    private String type;

    // Kích thước ảnh
    @Column(name = "size")
    private Long size;

    // Ghi chú
    @Column(name = "note")
    private String note;

    // Quan hệ n-1 với Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

    // Quan hệ n-1 với MasterLayout
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_layout_id")
    @JsonIgnore
    private MasterLayout masterLayout;

    // Quan hệ n-1 với RegionLink
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_link_id")
    @JsonIgnore
    private RegionLink regionLink;

    // Quan hệ n-1 với Utilities
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilities_id")
    @JsonIgnore
    private Utilities utilities;

    // Quan hệ 1-1 với Customer
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    // Quan hệ 1-1 với Employee
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

}
