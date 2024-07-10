package devcamp.realestateexchange.entity.location;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import devcamp.realestateexchange.entity.BaseEntity;
import devcamp.realestateexchange.entity.realestate.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "district")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class District extends BaseEntity {
    // Tên quận huyện
    @Column(name = "_name")
    private String name;

    // Tiền tố quận hay huyện
    @Column(name = "_prefix")
    private String prefix;

    // Tỉnh thành phố
    @ManyToOne
    @JoinColumn(name = "province_id")
    @JsonBackReference
    private Province province;

    // Phường xã
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Ward> wards;

    // Đường phố
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Street> streets;

    // Dự án
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Project> projects;

    // Bất động sản
    @OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Project> realEstates;
}
