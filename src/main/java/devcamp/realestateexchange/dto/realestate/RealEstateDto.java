package devcamp.realestateexchange.dto.realestate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.dto.location.StreetDto;
import devcamp.realestateexchange.dto.location.WardDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
import devcamp.realestateexchange.entity.social.Article;
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
    private String title;
    private Integer type;
    private Integer request;
    private BigDecimal price;
    private Integer priceUnit;
    private Double acreage;
    private Integer acreageUnit;
    private Integer bedroom;
    private Integer verify;
    private String customerName;
    private Address address;
    private RealEstateDetail detail;
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address {
        private String province;
        private String district;
        private String ward;
        private String street;
        private String address;
        private Double latitude;
        private Double longitude;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RealEstateDetail  {
        private String description;
        private BigDecimal priceMin;
        private String priceTime;
        private Integer totalFloors;
        private Integer numberFloors;
        private Integer bath;
        private String apartCode;
        private Double wallArea;
        private String landscapeView;
        private String direction;
        private String balcony;
        private Integer apartLoca;
        private Integer apartType;
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
        private String project;
        private String investor;
        private List<String> photoUrls;
    }
}


