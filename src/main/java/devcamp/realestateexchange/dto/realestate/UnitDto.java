package devcamp.realestateexchange.dto.realestate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnitDto {
    private Integer id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String phone2;
    private String fax;
    private String email;
    private String website;
    private String note;
}
