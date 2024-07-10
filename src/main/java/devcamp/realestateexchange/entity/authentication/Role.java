package devcamp.realestateexchange.entity.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import devcamp.realestateexchange.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "t_role")
@Getter
@Setter
public class Role extends BaseEntity {
    // Role name
    private String roleName;

    // Role key
    private String roleKey;

    // Quan hệ n-n với Permission
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<Permission> permissions = new HashSet<>();
}
