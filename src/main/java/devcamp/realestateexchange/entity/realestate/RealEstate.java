package devcamp.realestateexchange.entity.realestate;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import devcamp.realestateexchange.entity.location.AddressMap;
import devcamp.realestateexchange.entity.location.District;
import devcamp.realestateexchange.entity.location.Province;
import devcamp.realestateexchange.entity.location.Street;
import devcamp.realestateexchange.entity.location.Ward;
import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.media.Video;
import devcamp.realestateexchange.entity.social.Article;
import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.entity.user.UserReferenceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "real_estate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    // Mô tả chi tiết bđs
    @Column(name = "description")
    private String description;

    // Trạng thái xóa
    @Column(name = "deleted")
    private Boolean deleted = false;
    
    // Tỉnh thành phố
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

    // Địa chỉ chi tiết nhà.
    @Column(name = "address")
    private String address;

    // Id người đăng tin bđs
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    // Thông tin bài đăng
    @OneToOne(mappedBy = "realEstate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Article article;

    // Lưu đường dẫn ảnh
    @Column(name = "photos_url")
    private String photosUrl;

    // Lưu đường dẫn video
    @OneToMany(mappedBy = "realEstate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Video> videos;

    // Giá hiện tại đăng tin
    @Column(name = "price")
    private BigDecimal price;

    // Mã bất động sản
    @Column(name = "real_estate_code")
    private String realEstateCode;

    // Đơn vị giá: 0.Triệu, 1.Tỷ, 2.Triệu/m2, 3.Tỷ/m2
    @Column(name = "price_unit")
    private Integer priceUnit;


    // Thời gian bán
    // 1. Bán nhanh, 2. Bán chậm
    @Column(name = "price_time")
    private Integer priceTime;
    
    // diện tích bđs, diện tích thông thủy
    @Column(name = "acreage")
    private Double acreage;

    // Đơn vị diện tích: 0.m2, 1.ha
    @Column(name = "acreage_unit")
    private Integer acreageUnit;

    // Số phòng ngủ
    @Column(name = "bedroom")
    private Integer bedroom;

    // Đã xác minh hay chưa: 0.Chưa xác minh, 1.Đã xác minh
    @Column(name = "verify")
    private Integer verify = 0;
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
    // Kinh độ ở trên map
    @Column(name = "longitude")
    private Double longitude;
    // Vĩ độ ở trên map
    @Column(name = "latitude")
    private Double latitude;
    // Thông tin chi tiết bất động sản
    @Embedded
    private RealEstateDetail detail;

    // Thông tin chi tiết căn hộ
    @Embedded
    private ApartDetail apartDetail;
}
