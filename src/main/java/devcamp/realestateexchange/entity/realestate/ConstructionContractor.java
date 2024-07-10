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
@Table(name = "construction_contractor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionContractor extends UnitEntity {
   // Quan hệ 1-n với ProjectConstructionContractor
    @OneToMany(mappedBy = "constructionContractor", fetch = FetchType.LAZY)
    private Set<ProjectConstructionContractor> projectConstructionContractors;
}
