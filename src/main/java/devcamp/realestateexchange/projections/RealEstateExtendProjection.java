package devcamp.realestateexchange.projections;
import java.util.Date;

// Projection for RealEstateExtend. This projection is used to get the real estate with its article, detail, and apartment from the database.
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

