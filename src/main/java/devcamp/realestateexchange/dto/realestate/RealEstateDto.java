package devcamp.realestateexchange.dto.realestate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.dto.location.StreetDto;
import devcamp.realestateexchange.dto.location.WardDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
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
    private ProvinceDto province;
    private DistrictDto district;
    private WardDto ward;
    private StreetDto street;
    private CustomerDto customer;
    private Integer type;
    private String priceTime;
    private BigDecimal price;
    private Double acreage;
    private Date createdAt;
    private List<String> photoUrls;
    private String description;
    private String address;
    private Integer request;
    private Integer bedroom;
}
