package devcamp.realestateexchange.dto.realestate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import devcamp.realestateexchange.dto.location.AddressDto;
import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.dto.location.StreetDto;
import devcamp.realestateexchange.dto.location.WardDto;
import devcamp.realestateexchange.dto.social.ArticleDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.entity.realestate.RealEstateDetail;
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
public class RealEstateDto {
    private Integer id;
    // Tiêu đề tin
    private String title;
    // Danh mục tin đăng: 0.Đất, 1.Nhà ở,
    // 2.Căn hộ/Chung cư, 3.Văn phòng, Mặt bằng
    // 4.Kinh doanh, 5.Phòng trọ,
    private Integer type;
    // Nhu cầu 0.Cần bán, 2.Cần mua, 3.Cho thuê, 4.Cần thuê
    private Integer request;
    // Mô tả chi tiết bđs
    private String description;
    // Mã bất động sản
    private String realEstateCode;
    // Hướng nhà, căn hộ Đông: 1, Tây: 2, Bắc: 3, Nam: 4
    // Đông Bắc: 5, Tây Bắc: 6, Đông Nam: 7, Tây Nam: 8
    // Không rõ: 9
    private Integer direction;
    // Số tầng
    private Integer totalFloors;
    // Số nhà vệ sinh có
    private Integer bath;
    // Giá hiện tại đăng tin
    private BigDecimal price;
    // Đơn vị giá: 0.Triệu, 1.Tỷ, 2.Triệu/m2, 3.Tỷ/m2
    private Integer priceUnit;
    // Thời gian bán
    private String priceTime;
    // diện tích bđs, diện tích thông thủy
    private Double acreage;
    // Đơn vị diện tích: 0.m2, 1.ha
    private Integer acreageUnit;
    // Số phòng ngủ
    private Integer bedroom;
    // Đã xác minh hay chưa: 0.Chưa xác minh, 1.Đã xác minh
    private Integer verify;
    // Ngày đăng tin
    private Date createdAt;
    // Đường dẫn ảnh
    private List<String> photoUrls;
    // Id ảnh
    private List<Integer> photoIds;
    // Thông tin người đăng tin
    private CustomerDto customer;
    // Địa chỉ bđs
    private AddressDto address;
    // Article
    private ArticleDto article;
    // Chi tiết bđs
    private RealEstateDetailDto detail;
    // Chi tiết chung cư
    private ApartDetailDto apartDetail;
    // Id người đăng tin
    private Integer customerId;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RealEstateDetailDto {
        private BigDecimal priceMin;
        private String priceTime;
        private Double wallArea;
        private String landscapeView;
        private Integer balcony;
        private Integer furnitureType;
        private Integer furnitureStatus;
        private Integer priceRent;
        private Double returnRate;
        private Integer legalDoc;
        private Integer widthY;
        private Integer longX;
        private Integer streetHouse;
        private Integer FSBO;
        private String shape;
        private Integer distance2Facade;
        private Integer adjacentFacadeNum;
        private String adjacentRoad;
        private Integer alleyMinWidth;
        private Integer adjacentAlleyMinWidth;
        private String structure;
        private Integer DTSXD;
        private Integer ctxdPrice;
        private Integer ctxdValue;

        public RealEstateDetail toEntity() {
            RealEstateDetail detail = new RealEstateDetail();
            detail.setPriceMin(priceMin);
            detail.setWallArea(wallArea);
            detail.setLandscapeView(landscapeView);
            detail.setBalcony(balcony);
            detail.setFurnitureType(furnitureType);
            detail.setFurnitureStatus(furnitureStatus);
            detail.setPriceRent(priceRent);
            detail.setReturnRate(returnRate);
            detail.setLegalDoc(legalDoc);
            detail.setWidthY(widthY);
            detail.setLongX(longX);
            detail.setStreetHouse(streetHouse);
            detail.setFSBO(FSBO);
            detail.setShape(shape);
            detail.setDistance2Facade(distance2Facade);
            detail.setAdjacentFacadeNum(adjacentFacadeNum);
            detail.setAdjacentRoad(adjacentRoad);
            detail.setAlleyMinWidth(alleyMinWidth);
            detail.setAdjacentAlleyMinWidth(adjacentAlleyMinWidth);
            detail.setStructure(structure);
            detail.setDTSXD(DTSXD);
            detail.setCtxdPrice(ctxdPrice);
            detail.setCtxdValue(ctxdValue);
            return detail;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApartDetailDto {
        private String apartCode;
        private Integer apartLoca;
        private Integer apartType;
        private Integer numberFloors;
    }

    public RealEstateDto(Integer id, String title, Integer type, Integer request, BigDecimal price, Integer priceUnit,
            Double acreage, Integer acreageUnit, Integer bedroom, Integer verify, Date createdAt) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.request = request;
        this.price = price;
        this.priceUnit = priceUnit;
        this.acreage = acreage;
        this.acreageUnit = acreageUnit;
        this.bedroom = bedroom;
        this.verify = verify;
        this.createdAt = createdAt;
    }

    // Constructor for getting multiple real estate
    public RealEstateDto(Integer id, String title, Integer type, Integer request, BigDecimal price, Integer priceUnit,
            Double acreage, Integer acreageUnit, Integer bedroom, Integer verify, Date createdAt, AddressDto address) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.request = request;
        this.price = price;
        this.priceUnit = priceUnit;
        this.acreage = acreage;
        this.acreageUnit = acreageUnit;
        this.bedroom = bedroom;
        this.verify = verify;
        this.createdAt = createdAt;
        this.address = address;
    }

    public RealEstateDto(Integer id, String title, Integer type, Integer request, BigDecimal price, Integer priceUnit,
            Double acreage, Integer acreageUnit, Integer bedroom, Integer verify, Date createdAt, CustomerDto customer,
            AddressDto address) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.request = request;
        this.price = price;
        this.priceUnit = priceUnit;
        this.acreage = acreage;
        this.acreageUnit = acreageUnit;
        this.bedroom = bedroom;
        this.verify = verify;
        this.createdAt = createdAt;
        this.customer = customer;
        this.address = address;
    }

    public RealEstateDto(Integer id, String title, Integer type, Integer request, String realEstateCode,
            BigDecimal price, Integer priceUnit, String priceTime, Double acreage, Integer acreageUnit, Integer bedroom,
            Integer verify, Date createdAt, Integer customerId) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.request = request;
        this.realEstateCode = realEstateCode;
        this.price = price;
        this.priceUnit = priceUnit;
        this.priceTime = priceTime;
        this.acreage = acreage;
        this.acreageUnit = acreageUnit;
        this.bedroom = bedroom;
        this.verify = verify;
        this.createdAt = createdAt;
        this.customerId = customerId;
    }

    public RealEstateDto(RealEstate realEstate) {
        this.id = realEstate.getId();
        this.title = realEstate.getTitle();
        this.type = realEstate.getType();
        this.description = realEstate.getDescription();
        this.request = realEstate.getRequest();
        this.price = realEstate.getPrice();
        this.priceUnit = realEstate.getPriceUnit();
        this.priceTime = realEstate.getPriceTime();
        this.acreage = realEstate.getAcreage();
        this.acreageUnit = realEstate.getAcreageUnit();
        this.bedroom = realEstate.getBedroom();
        this.verify = realEstate.getVerify();
        this.createdAt = realEstate.getCreatedAt();
        this.direction = realEstate.getDirection();
        this.totalFloors = realEstate.getTotalFloors();
        this.bath = realEstate.getBath();
        this.photoUrls = realEstate.getPhotos().stream().map(photo -> photo.getUrl())
                .collect(java.util.stream.Collectors.toList());

        this.customer = new CustomerDto(realEstate.getCustomer().getId(), realEstate.getCustomer().getFullName(),
                realEstate.getCustomer().getPhoto().getUrl());

        this.address = new AddressDto(realEstate.getAddress(),
                new ProvinceDto(realEstate.getProvince().getId(), realEstate.getProvince().getName()),
                new DistrictDto(realEstate.getDistrict().getId(), realEstate.getDistrict().getName(),
                        realEstate.getDistrict().getPrefix()),
                new WardDto(realEstate.getWard().getId(), realEstate.getWard().getName(),
                        realEstate.getWard().getPrefix()),
                new StreetDto(realEstate.getStreet().getId(), realEstate.getStreet().getName(),
                        realEstate.getStreet().getPrefix()),
                realEstate.getAddressMap().getLatitude(),
                realEstate.getAddressMap().getLongitude());

        
        this.article = new ArticleDto(realEstate.getArticle());

        this.detail = new RealEstateDetailDto();
        this.detail.setPriceMin(realEstate.getDetail().getPriceMin());
        this.detail.setWallArea(realEstate.getDetail().getWallArea());
        this.detail.setLandscapeView(realEstate.getDetail().getLandscapeView());
        this.detail.setBalcony(realEstate.getDetail().getBalcony());
        this.detail.setFurnitureType(realEstate.getDetail().getFurnitureType());
        this.detail.setFurnitureStatus(realEstate.getDetail().getFurnitureStatus());
        this.detail.setPriceRent(realEstate.getDetail().getPriceRent());
        this.detail.setReturnRate(realEstate.getDetail().getReturnRate());
        this.detail.setLegalDoc(realEstate.getDetail().getLegalDoc());
        this.detail.setWidthY(realEstate.getDetail().getWidthY());
        this.detail.setLongX(realEstate.getDetail().getLongX());
        this.detail.setStreetHouse(realEstate.getDetail().getStreetHouse());
        this.detail.setFSBO(realEstate.getDetail().getFSBO());
        this.detail.setShape(realEstate.getDetail().getShape());
        this.detail.setDistance2Facade(realEstate.getDetail().getDistance2Facade());
        this.detail.setAdjacentFacadeNum(realEstate.getDetail().getAdjacentFacadeNum());
        this.detail.setAdjacentRoad(realEstate.getDetail().getAdjacentRoad());
        this.detail.setAlleyMinWidth(realEstate.getDetail().getAlleyMinWidth());
        this.detail.setAdjacentAlleyMinWidth(realEstate.getDetail().getAdjacentAlleyMinWidth());
        this.detail.setStructure(realEstate.getDetail().getStructure());
        this.detail.setDTSXD(realEstate.getDetail().getDTSXD());
        this.detail.setCtxdPrice(realEstate.getDetail().getCtxdPrice());
        this.detail.setCtxdValue(realEstate.getDetail().getCtxdValue());

        if (this.type == 2) {
            this.apartDetail = new ApartDetailDto();
            this.apartDetail.setApartCode(realEstate.getApartDetail().getApartCode());
            this.apartDetail.setApartLoca(realEstate.getApartDetail().getApartLoca());
            this.apartDetail.setApartType(realEstate.getApartDetail().getApartType());
            this.apartDetail.setNumberFloors(realEstate.getApartDetail().getNumberFloors());
        }
    }

    public static RealEstateDto fromFields(List<?> fields) {
        RealEstateDto dto = new RealEstateDto();
        dto.id = (Integer) fields.get(0);
        dto.title = (String) fields.get(1);
        dto.type = (Integer) fields.get(2);
        dto.request = (Integer) fields.get(3);
        dto.realEstateCode = (String) fields.get(4);
        dto.price = (BigDecimal) fields.get(5);
        dto.priceUnit = (Integer) fields.get(6);
        dto.priceTime = (String) fields.get(7);
        dto.acreage = (Double) fields.get(8);
        dto.acreageUnit = (Integer) fields.get(9);
        dto.bedroom = (Integer) fields.get(10);
        dto.verify = (Integer) fields.get(11);
        dto.createdAt = (Date) fields.get(12);
        dto.customerId = (Integer) fields.get(13);
        return dto;
    }
    
}
