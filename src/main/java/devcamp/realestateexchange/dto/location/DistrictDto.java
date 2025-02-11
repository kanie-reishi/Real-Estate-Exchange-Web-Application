package devcamp.realestateexchange.dto.location;

import devcamp.realestateexchange.entity.location.District;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictDto {
    // Id quận/huyện
    private Integer id;
    // Tên quận/huyện
    private String name;
    // Tiền tố (VD: Quận, Huyện)
    private String prefix;
    // Id tỉnh/thành phố
    private Integer province_id;
    // Tên Tỉnh thành phố
    private String province_name;
    public DistrictDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public DistrictDto(Integer id, String name, String prefix) {
        this.id = id;
        this.name = name;
        this.prefix = prefix;
    }
}
