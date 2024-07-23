package devcamp.realestateexchange.entity.realestate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ApartDetail {
    // Apartment code, mã căn hộ
    @Column(name = "apart_code")
    private String apartCode;

    // Apartment location, vi trí căn hộ: căn góc, căn thường
    @Column(name = "apart_loca")
    private Integer apartLoca;

    // Loại căn hộ, cao cấp, văn phòng, bình dân,
    @Column(name = "apart_type")
    private Integer apartType;

    // Tầng số bao nhiêu, giành cho bán căn hộ chung cư
    @Column(name = "number_floors")
    private Integer numberFloors;
}
