package devcamp.realestateexchange.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealEstateDto {
    private int id;
    private String title;
    private int provinceId;
    private int districtId;
    private BigDecimal price;
    private double acreage;
    private Date dateCreate;
    private List<String> photoUrls;
    private String description;
    private String address;
    public RealEstateDto(Integer id, String title, String description, BigDecimal price, Double acreage, Integer provinceId, Integer districtId, String address, Date dateCreate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.acreage = acreage;
        this.provinceId = provinceId;
        this.districtId = districtId;
        this.address = address;
        this.dateCreate = dateCreate;
    }
}
