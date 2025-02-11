package devcamp.realestateexchange.dto.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WardDto {
    // Id phường/xã
    private Integer id;
    // Tên phường/xã
    private String name;
    // Tiền tố (VD: Phường, Xã)
    private String prefix;
    // Id tỉnh/thành phố
    private Integer province_id;
    // Id quận/huyện
    private Integer district_id;
    // Tên tỉnh thành phố
    private String province_name;
    // Tên quận huyện
    private String district_name;
    public WardDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public WardDto(Integer id, String name, String prefix) {
        this.id = id;
        this.name = name;
        this.prefix = prefix;
    }
}
