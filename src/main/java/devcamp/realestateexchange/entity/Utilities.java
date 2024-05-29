package devcamp.realestateexchange.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
@Entity
@Table(name = "utilities")
public class Utilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotNull(message = "Name is required")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "photo")
    private String photo;
    @OneToMany(mappedBy = "utilities", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProjectUtilities> projectUtilities;
    public Utilities() {
    }
    public Utilities(int id, @NotNull(message = "Name is required") String name, String description, String photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
