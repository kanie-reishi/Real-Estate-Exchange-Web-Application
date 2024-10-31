package devcamp.realestateexchange.dto.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    // Địa chỉ chi tiết
    private String addressDetail;
    // Tỉnh/Thành phố
    private ProvinceDto province;
    // Quận/Huyện
    private DistrictDto district;
    // Phường/Xã
    private WardDto ward;
    // Đường
    private StreetDto street;
    // Tọa độ
    private Double latitude;
    private Double longitude;
}
