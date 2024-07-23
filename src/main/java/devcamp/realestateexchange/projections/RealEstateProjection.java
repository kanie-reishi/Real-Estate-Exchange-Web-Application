package devcamp.realestateexchange.projections;
import java.util.Date;
public interface RealEstateProjection {
    Integer getId();
    String getTitle();
    Integer getType();
    Integer getRequest();
    Double getPrice();
    String getPriceUnit();
    Double getAcreage();
    String getAcreageUnit();
    Integer getBedroom();
    Integer getVerify();
    String getRealEstateCode();
    Date getCreatedAt();

    CustomerProjection getCustomer();
    ProvinceProjection getProvince();
    DistrictProjection getDistrict();
    WardProjection getWard();
    StreetProjection getStreet();
    ArticleProjection getArticle();
    RealEstateDetailProjection getRealEstateDetail();
}

