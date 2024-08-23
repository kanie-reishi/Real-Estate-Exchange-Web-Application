package devcamp.realestateexchange.entity.realestate;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import devcamp.realestateexchange.entity.location.AddressMap;
import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.user.UserReferenceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "region_link")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionLink extends UserReferenceEntity {
    // Tên khu vực
    @Column(name = "name")
    @NotNull(message = "Name is required")
    private String name;

    // Mô tả về khu vực
    @Column(name = "description")
    private String description;

    // Địa chỉ
    @Column(name = "address")
    private String address;
    
    // Quan hệ 1-n với Photo
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_link_id")
    private Set<Photo> photos;

    // kinh độ
    @Column(name = "_lng")
    private Double longitude;

    // vĩ độ
    @Column(name = "_lat")
    private Double latitude;
    
    // Quan hệ n-n với Project
    @ManyToMany(mappedBy = "regionLinks")
    private Set<Project> projects;
}
