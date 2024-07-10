package devcamp.realestateexchange.entity.realestate;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import devcamp.realestateexchange.entity.location.AddressMap;
import devcamp.realestateexchange.entity.location.District;
import devcamp.realestateexchange.entity.location.Province;
import devcamp.realestateexchange.entity.location.Street;
import devcamp.realestateexchange.entity.location.Ward;
import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.entity.user.UserReferenceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "real_estate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "RealEstate.photos",
    attributeNodes = @NamedAttributeNode("photos"))
public class RealEstate extends UserReferenceEntity {
    // Tiêu đề tin
    @Column(name = "title")
    private String title;

    // Danh mục tin đăng: 0.Đất, 1.Nhà ở, 
    // 2.Căn hộ/Chung cư, 3.Văn phòng, Mặt bằng 
    // 4.Kinh doanh, 5.Phòng trọ,
    @Column(name = "type")
    private Integer type;

    // Nhu cầu 0.Cần bán, 2.Cần mua, 3.Cho thuê, 4.Cần thuê
    @Column(name = "request")
    private Integer request;

    // 	Tỉnh thành phố
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    @JsonBackReference
    private Province province;

    // Quận huyện
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    @JsonBackReference
    private District district;

    // Phường xã
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    @JsonBackReference
    private Ward ward;

    // Thông tin tên đường
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "street_id")
    @JsonBackReference
    private Street street;

    // Nhà thuộc dự án nào.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;
    
    // Địa chỉ trên map
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_map_id")
    private AddressMap addressMap;

    // Địa chỉ chi tiết nhà.
    @Column(name = "address")
    private String address;

    // Id người đăng tin bđs 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    // Lưu đường dẫn ảnh
    @OneToMany(mappedBy = "realEstate", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Photo> photos;

    // Giá hiện tại đăng tin
    @Column(name = "price")
    private BigDecimal price;

    // Giá bán tối thiểu
    @Column(name = "price_min")
    private BigDecimal priceMin;

    // 	Thời gian bán: 1.Bán nhanh, 2.Bán chậm
    @Column(name = "price_time")
    private Integer priceTime;

    // diện tích bđs, diện tích thông thủy
    @Column(name = "acreage")
    private Double acreage;

    // 	Hướng nhà, căn hộ Đông: 1, Tây: 2, Bắc: 3, Nam: 4
    // Đông Bắc: 5, Tây Bắc: 6, Đông Nam: 7, Tây Nam: 8
    // Không rõ: 9
    @Column(name = "direction")
    private Integer direction;

    // Tổng số tầng nhà có
    @Column(name = "total_floors")
    private Integer totalFloors;

    // Tầng số bao nhiêu, giành cho bán căn hộ chung cư
    @Column(name = "number_floors")
    private Integer numberFloors;

    // Số nhà vệ sinh có
    @Column(name = "bath")
    private Integer bath;

    // Apartment code, mã căn hộ
    @Column(name = "apart_code")
    private String apartCode;

    // 	Diện tích tim tường
    @Column(name = "wall_area")
    private Double wallArea;

    // 	Số phòng ngủ
    @Column(name = "bedroom")
    private Integer bedroom;

    // 	Số Ban công, lô gia
    @Column(name = "balcony")
    private Integer balcony;

    // View cảnh quan, 0: chưa cập nhật, 1: hồ bơi
    @Column(name = "landscape_view")    
    private String landscapeView;

    // Apartment location, vi trí căn hộ: căn góc, căn thường
    @Column(name = "apart_loca")
    private Integer apartLoca;

    // Loại căn hộ, cao cấp, văn phòng, bình dân,
    @Column(name = "apart_type")
    private Integer apartType;

    // Loại nội thất, cơ bản: 0, đầy đủ: 1, chưa biết: 3.
    @Column(name = "furniture_type")
    private Integer furnitureType;

    // Giá cho thuê
    @Column(name = "price_rent")
    private Integer priceRent;

    // Tỷ suất sinh lời
    @Column(name = "return_rate")
    private Double returnRate;

    // 	Pháp lý: 0.Sổ đỏ, 1.Sổ hồng, 2.Sổ đỏ chính chủ, 3.Sổ hồng chính chủ
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

}
