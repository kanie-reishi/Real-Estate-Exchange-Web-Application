package devcamp.realestateexchange.entity.realestate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import devcamp.realestateexchange.entity.location.AddressMap;
import devcamp.realestateexchange.entity.user.UserReferenceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public abstract class UnitEntity extends UserReferenceEntity {
    // Tên đơn vị
    @Column(name = "name")
    @NotNull(message = "Name is required")
    private String name;

    // Mô tả về đơn vị
    @Column(name = "description")
    private String description;

    // Địa chỉ map
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private AddressMap addressMap;

    // Số điện thoại
    @Column(name = "phone")
    private String phone;

    // Số điện thoại 2
    @Column(name = "phone2")
    private String phone2;

    // Fax
    @Column(name = "fax")
    private String fax;

    // Email
    @Column(name = "email")
    private String email;

    // Website
    @Column(name = "website")
    private String website;

    // Ghi chú
    @Column(name = "note")
    private String note;
}
