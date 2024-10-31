package devcamp.realestateexchange.dto.realestate;

import java.util.List;

import devcamp.realestateexchange.dto.location.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionLinkDto {
    // Id liên kết vùng
    private Integer id;
    // Tên liên kết vùng
    private String name;
    // Mô tả liên kết vùng
    private String description;
    // Danh sách ảnh liên kết vùng
    private List<String> photos;
    // Danh sách dự án liên kết vùng
    private List<ProjectDto> projects;
    // Danh sách bất động sản liên kết vùng
    private List<String> photoUrls;
    // Địa chỉ liên kết vùng
    private AddressDto address;
}
