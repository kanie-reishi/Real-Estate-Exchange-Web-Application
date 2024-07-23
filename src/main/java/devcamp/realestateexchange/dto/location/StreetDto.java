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
    private Integer id;
    private String name;
    private String prefix;
    public StreetDto(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
}
