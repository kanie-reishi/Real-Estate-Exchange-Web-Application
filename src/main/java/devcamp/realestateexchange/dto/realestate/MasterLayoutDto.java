package devcamp.realestateexchange.dto.realestate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MasterLayoutDto {
    private Integer id;
    private String name;
    private String description;
    private Double acreage;
    private String apartmentList;
    private List<String> photoUrls;
}
