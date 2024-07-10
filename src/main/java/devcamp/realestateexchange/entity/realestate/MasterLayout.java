package devcamp.realestateexchange.entity.realestate;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.user.UserReferenceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "master_layout")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MasterLayout extends UserReferenceEntity {
    // Tên layout
    @Column(name = "name")
    @NotNull(message = "Name is required")
    private String name;

    // Mô tả về layout
    @Column(name = "description")
    private String description;

    // Quan hệ n-n với Project
    @ManyToMany(mappedBy = "masterLayouts", cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    private List<Project> projects;

    // Diện tích
    @Column(name = "acreage")
    private Double acreage;

    // Danh sách căn hộ điển hình của mặt bằng, dùng để copy cho các căn hộ khác.
    @Column(name = "apartment_list")
    private String apartmentList;

    // Quan hệ 1-n với Photo
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "master_layout_id")
    private Set<Photo> photos;
    
}
