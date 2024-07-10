package devcamp.realestateexchange.entity.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import devcamp.realestateexchange.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address_map")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressMap extends BaseEntity {
    // Địa chỉ
    @Column(name = "address")
    private String address;
    // Vĩ độ
    @Column(name = "_lat")
    double latitude;
    // Kinh độ
    @Column(name = "_long")
    double longitude;
}
