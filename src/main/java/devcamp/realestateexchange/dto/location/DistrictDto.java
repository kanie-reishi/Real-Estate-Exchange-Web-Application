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

    public DistrictDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
