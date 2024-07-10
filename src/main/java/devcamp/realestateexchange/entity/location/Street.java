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
@Table(name = "street")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Street extends BaseEntity {
    // Tên đường phố
    @Column(name = "_name")
    private String name;

    // Tiền tố đường hay phố
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
    @OneToMany(mappedBy = "street", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Project> projects;
    
    // Bất động sản
    @OneToMany(mappedBy = "street", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<RealEstate> realEstates;

}
