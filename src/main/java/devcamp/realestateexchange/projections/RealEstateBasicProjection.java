package devcamp.realestateexchange.projections;

import java.math.BigDecimal;
import java.util.Date;

public interface RealEstateBasicProjection {
    Integer getId();
    String getTitle();
    Integer getType();
    Integer getRequest();
    BigDecimal getPrice();
    Integer getPriceUnit();
    Double getAcreage();
    Integer getAcreageUnit();
    Integer getBedroom();
    Integer getVerify();
    String getRealEstateCode();
    Date getCreatedAt();

    CustomerProjection getCustomer();
    ProvinceProjection getProvince();
    DistrictProjection getDistrict();
}
