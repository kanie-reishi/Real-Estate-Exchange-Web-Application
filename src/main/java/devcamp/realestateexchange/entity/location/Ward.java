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
import devcamp.realestateexchange.entity.realestate.RealEstate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ward")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ward extends BaseEntity {
    // Tên phường xã
    @Column(name = "_name")
    private String name;

    // Là phường hay xã
    @Column(name = "_prefix")
    private String prefix;

    // Quận huyện
    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonBackReference
    private District district;

    // Tỉnh thành phố
    @ManyToOne
    @JoinColumn(name = "province_id")
    @JsonBackReference
    private Province province;

    // Dự án
    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Project> projects;

    // Bất động sản
    @OneToMany(mappedBy = "ward", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<RealEstate> realEstates;
}
