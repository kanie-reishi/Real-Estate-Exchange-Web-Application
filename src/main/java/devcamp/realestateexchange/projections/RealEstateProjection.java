package devcamp.realestateexchange.projections;

import java.math.BigDecimal;
import java.util.Date;

// Projection for RealEstate. This projection is used to get the real estate with its customer, province, district, ward, street, and project from the database.
public interface RealEstateProjection {
    Integer getId();
    String getTitle();
    Integer getType();
    Integer getRequest();
    BigDecimal getPrice();
    Integer getPriceTime();
    Integer getPriceUnit();
    Double getAcreage();
    Integer getAcreageUnit();
    Integer getBedroom();
    Integer getVerify();
    String getRealEstateCode();
    Date getCreatedAt();
    String getAddress();
    Integer getDirection();
    Integer getTotalFloors();
    Integer getBath();
    String getDescription();
    Double getLatitude();
    Double getLongitude();
    String getPhotosUrl();

    CustomerProjection getCustomer();
    ProvinceProjection getProvince();
    DistrictProjection getDistrict();
    WardProjection getWard();
    StreetProjection getStreet(); 
}
