package devcamp.realestateexchange.entity.realestate;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.search.annotations.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RealEstateDetail {
    // Hướng nhà, căn hộ Đông: 1, Tây: 2, Bắc: 3, Nam: 4
    // Đông Bắc: 5, Tây Bắc: 6, Đông Nam: 7, Tây Nam: 8
    // Không rõ: 9
    @Column(name = "direction")
    private Integer direction;

    // Tổng số tầng nhà có
    @Column(name = "total_floors")
    private Integer totalFloors;

    // Số nhà vệ sinh có
    @Column(name = "bath")
    private Integer bath;

    // Diện tích tim tường
    @Column(name = "wall_area")
    private Double wallArea;
    // Số Ban công, lô gia
    @Column(name = "balcony")
    private Integer balcony;

    // View cảnh quan, 0: chưa cập nhật, 1: hồ bơi
    @Column(name = "landscape_view")
    private String landscapeView;

    // Loại nội thất, cơ bản: 0, đầy đủ: 1, chưa biết: 3.
    @Column(name = "furniture_type")
    private Integer furnitureType;

    // Tình trạng nội thất
    @Column(name = "furniture_status")
    private Integer furnitureStatus;

    // Giá cho thuê
    @Column(name = "price_rent")
    private Integer priceRent;

    // Tỷ suất sinh lời
    @Column(name = "return_rate")
    private Double returnRate;

    // Pháp lý: 0.Sổ đỏ, 1.Sổ hồng, 2.Sổ đỏ chính chủ, 3.Sổ hồng chính chủ
    @Column(name = "legal_doc")
    private Integer legalDoc;

    // Mô tả chi tiết bđs
    @Column(name = "description")
    private String description;

    // Chiều rộng
    @Column(name = "width_y")
    private Integer widthY;

    // Chiều dài
    @Column(name = "long_x")
    private Integer longX;

    // Nhà có phải mặt đường hay không?
    @Column(name = "street_house")
    private Integer streetHouse;

    // Nhà đăng bởi chủ sở hữu hay không?
    @Column(name = "FSBO")
    private Integer FSBO;

    // Hình dáng ngôi nhà: Khá cân đối, Cân đối
    @Column(name = "shape")
    private String shape;

    // Khoảng cách ra mặt tiền đường (m)
    @Column(name = "distance2facade")
    private Integer distance2Facade;

    // Số mặt tiền tiếp giáp
    @Column(name = "adjacent_facade_num")
    private Integer adjacentFacadeNum;

    // Đường tiếp giáp Bê tông
    @Column(name = "adjacent_road")
    private String adjacentRoad;

    // Độ rộng đường hẻm nhỏ nhất (m)
    @Column(name = "alley_min_width")
    private Integer alleyMinWidth;

    // Độ rộng đường hẻm tiếp giáp (m)
    @Column(name = "adjacent_alley_min_width")
    private Integer adjacentAlleyMinWidth;

    // Yếu tố khác: Gần chợ
    @Column(name = "factor")
    private Integer factor;

    // Kết cấu: Tường gạch, sàn BTCT, mái BTCT + tôn
    @Column(name = "structure")
    private String structure;

    // Diện tích sàn xây dựng =
    // diện tích sàn sử dụng (1)
    // + (2) diện tích khác (trần WC, phần móng, mái, sân, tầng hầm…) (m2)
    @Column(name = "DTSXD")
    private Integer DTSXD;

    // Đơn giá Công trình xây dựng (đồng/m2)
    @Column(name = "CTXD_price")
    private Integer ctxdPrice;

    // Giá trị Công trình xây dựng (đồng)
    @Column(name = "CTXD_value")
    private Integer ctxdValue;

    // Giá bán tối thiểu
    @Column(name = "price_min")
    private BigDecimal priceMin;
}
