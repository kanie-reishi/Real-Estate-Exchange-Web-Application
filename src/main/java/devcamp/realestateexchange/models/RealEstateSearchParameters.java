package devcamp.realestateexchange.models;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateSearchParameters extends SearchParameters {
    // Danh mục tin đăng: 0.Đất, 1.Nhà ở,
    // 2.Căn hộ/Chung cư, 3.Văn phòng, Mặt bằng
    // 4.Kinh doanh, 5.Phòng trọ,
    private Integer type;
    // Nhu cầu 0.Cần bán, 2.Cần mua, 3.Cho thuê, 4.Cần thuê
    private Integer request;
    // Mã bất động sản
    private String realEstateCode;
    // Giá hiện tại đăng tin
    private BigDecimal price;
    // Đơn vị giá: 0.Triệu, 1.Tỷ, 2.Triệu/m2, 3.Tỷ/m2
    private Integer priceUnit;
    // diện tích bđs, diện tích thông thủy
    private Double acreage;
    // Đơn vị diện tích: 0.m2, 1.ha
    private Integer acreageUnit;
    // Số phòng ngủ
    private Integer bedroom;
    // Số phòng tắm
    private Integer bathroom;
    // Số tầng
    private Integer floor;
    // Hướng nhà, căn hộ Đông: 1, Tây: 2, Bắc: 3, Nam: 4
    // Đông Bắc: 5, Tây Bắc: 6, Đông Nam: 7, Tây Nam: 8
    // Không rõ: 9
    private Integer direction;
    // Giá tối thiểu
    private BigDecimal minPrice;
    // Giá tối đa
    private BigDecimal maxPrice;
    // Diện tích tối thiểu
    private Double minAcreage;
    // Diện tích tối đa
    private Double maxAcreage;
    // Đã xác minh hay chưa: 0.Chưa xác minh, 1.Đã xác minh
    private Integer verify;
    // Ngày đăng tin
    private Date createdAt;
    // Id người đăng tin
    private Integer customerId;
    // Id tỉnh
    private Integer provinceId;
    // Id quận huyện
    private Integer districtId;
    // Id phường xã
    private Integer wardId;
}
