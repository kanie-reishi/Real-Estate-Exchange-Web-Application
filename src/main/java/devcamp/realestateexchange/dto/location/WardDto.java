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

    public WardDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
