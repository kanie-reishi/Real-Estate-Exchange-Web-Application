package devcamp.realestateexchange.dto.realestate;

import java.util.Date;
import java.util.List;

import devcamp.realestateexchange.dto.location.AddressDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
import devcamp.realestateexchange.entity.realestate.MasterLayout;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDto {
    // Id của dự án
    private Integer id;
    // Tên dự án
    private String name;
    // Phương châm của dự án
    private String slogan;
    // Mô tả về dự án
    private String description;
    // Diện tích tổng của dự án
    private Double acreage;
    // Diện tích xây dựng
    private Double constructArea;
    // Số block dự án có
    private Integer numBlock;
    // Số tầng, có thể là khoảng tương đối nên có kiểu text (20-38)
    private String numFloors;
    // Tổng số căn hộ.
    private Integer numApartment;
    // Diện tích căn hộ, có thể là khoảng
    private String apartmentArea;
    // Thông tin ngày tạo
    private String createdAt;
    // Thông tin ngày cập nhật
    private String updatedAt;
    // Danh sách các hình ảnh của dự án
    private List<String> images;
    // Danh sách các video của dự án
    private List<String> videos;
    // Danh sách các tiện ích của dự án
    private List<UtilitiesDto> utilities;
    // Danh sách các tiện ích xung quanh của dự án
    private List<RegionLinkDto> regionLinks;
    // Danh sách master layout của dự án
    private List<MasterLayoutDto> masterLayouts;
    // Danh sách các bds của dự án
    private List<RealEstateDto> realEstates;
    // Danh sách các nhà thầu của dự án
    private List<ContractorDto> contractors;
    // Danh sách các nhà đầu tư của dự án
    private List<InvestorDto> investors;
    // Danh sách các đơn vị thiết kế của dự án
    private List<DesignUnitDto> designUnits;
    // Thông tin người đăng tin
    private CustomerDto customer;
    // Địa chỉ bđs
    private AddressDto addressDetail;
}
