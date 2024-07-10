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
@Table(name = "investor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Investor extends UnitEntity {
    // Quan hệ 1-n với ProjectInvestor
    @OneToMany(mappedBy = "investor", fetch = FetchType.LAZY)
    private Set<ProjectInvestor> projectInvestors;
}
