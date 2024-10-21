package devcamp.realestateexchange.projections;
import java.util.Date;
public interface RealEstateExtendProjection extends RealEstateProjection {
    ArticleProjection getArticle();
    RealEstateDetailProjection getDetail();
    ApartmentProjection getApartDetail();

    interface ApartmentProjection {
        String getApartCode();
        Integer getApartLoca();
        Integer getApartType();
        Integer getNumberFloors();
    }
}

