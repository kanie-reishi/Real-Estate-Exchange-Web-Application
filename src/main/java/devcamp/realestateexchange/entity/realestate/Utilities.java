package devcamp.realestateexchange.entity.realestate;

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
@Table(name = "utilities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Utilities extends UserReferenceEntity{
    // Tên tiện ích
    @Column(name = "name")
    @NotNull(message = "Name is required")
    private String name;

    // Mô tả về tiện ích
    @Column(name = "description")
    private String description;

    // Ảnh
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "utilities_id")
    private Set<Photo> photos;

    // Quan hệ n-n với Project
    @ManyToMany(mappedBy = "utilities", cascade = {CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    private Set<Project> projects;
}
