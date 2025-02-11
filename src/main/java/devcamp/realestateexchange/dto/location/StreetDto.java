package devcamp.realestateexchange.dto.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StreetDto {
    // Id đường
    private Integer id;
    // Tên đường
    private String name;
    // Tiền tố (VD: Đường, Phố)
    private String prefix;
    // Id tỉnh/thành phố
    private Integer province_id;
    // Id quận/huyện
    private Integer district_id;
    // Tên tỉnh thành phố
    private String province_name;
    // Tên quận huyện
    private String district_name;
    // Số lượng bất động sản
    private Long real_estate_count;
    public StreetDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public StreetDto(Integer id, String name, Long real_estate_count) {
        this.id = id;
        this.name = name;
        this.real_estate_count = real_estate_count;
    }
    public StreetDto(Integer id, String name, String prefix) {
        this.id = id;
        this.name = name;
        this.prefix = prefix;
    }
}
