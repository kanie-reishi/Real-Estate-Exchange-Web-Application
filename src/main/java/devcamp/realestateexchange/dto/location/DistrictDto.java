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
    private Integer id;
    private String name;
    private String prefix;
    public DistrictDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
   
}
