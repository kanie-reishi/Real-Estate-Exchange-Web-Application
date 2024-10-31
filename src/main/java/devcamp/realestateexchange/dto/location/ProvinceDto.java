package devcamp.realestateexchange.dto.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceDto {
    // Id tỉnh/thành phố
    private Integer id;
    // Tên tỉnh/thành phố
    private String name;

    // Mã tỉnh/thành phố
    private String code;

    public ProvinceDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
