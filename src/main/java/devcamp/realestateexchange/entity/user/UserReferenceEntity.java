package devcamp.realestateexchange.entity.user;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import devcamp.realestateexchange.entity.BaseEntity;
import devcamp.realestateexchange.entity.authentication.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserReferenceEntity extends BaseEntity {
    // Người tạo
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    // Người cập nhật
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

}
