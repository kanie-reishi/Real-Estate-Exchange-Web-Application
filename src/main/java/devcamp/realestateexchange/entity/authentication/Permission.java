package devcamp.realestateexchange.entity.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

import devcamp.realestateexchange.entity.BaseEntity;

@Entity
@Table(name = "t_permission")
@Getter
@Setter
public class Permission extends BaseEntity {
    // Tên quyền
    private String permissionName;

    // Mã quyền
    private String permissionKey;
}

