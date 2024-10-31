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

    public StreetDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

}
