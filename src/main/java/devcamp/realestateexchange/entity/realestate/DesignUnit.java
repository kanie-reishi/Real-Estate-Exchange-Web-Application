package devcamp.realestateexchange.entity.realestate;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "design_unit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DesignUnit extends UnitEntity {
    // Quan hệ 1-n với ProjectDesignUnit
    @OneToMany(mappedBy = "designUnit", fetch = FetchType.LAZY)
    private Set<ProjectDesignUnit> projectDesignUnits;

}
