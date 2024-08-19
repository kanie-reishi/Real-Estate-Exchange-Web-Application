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
public class UtilitiesDto {
    private Integer id;
    private String name;
    private String description;
    private List<String> photos;
    private List<ProjectDto> projects;
}
