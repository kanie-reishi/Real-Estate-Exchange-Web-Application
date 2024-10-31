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
    // id bản vẽ mặt bằng
    private Integer id;
    // Tên bản vẽ mặt bằng
    private String name;
    // Mô tả bản vẽ mặt bằng
    private String description;
    // Diện tích
    private Double acreage;
    // Danh sách các căn hộ trong bản vẽ
    private String apartmentList;
    // Danh sách ảnh bản vẽ
    private List<String> photoUrls;
}
