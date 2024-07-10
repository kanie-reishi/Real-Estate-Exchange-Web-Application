package devcamp.realestateexchange.entity.realestate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import devcamp.realestateexchange.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDesignUnit extends BaseEntity {
    // Quan hệ n-1 với Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    
    // Quan hệ n-1 với DesignUnit
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "design_unit_id")
    private DesignUnit designUnit;
}
