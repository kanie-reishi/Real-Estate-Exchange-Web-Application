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
    // id tiện ích
    private Integer id;
    // Tên tiện ích
    private String name;
    // Mô tả tiện ích
    private String description;
    // Danh sách ảnh tiện ích
    private List<String> photos;
    // Danh sách dự án tiện ích
    private List<ProjectDto> projects;
}
