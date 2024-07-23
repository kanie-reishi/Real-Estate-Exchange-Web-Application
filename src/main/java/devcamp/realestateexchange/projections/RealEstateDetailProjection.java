package devcamp.realestateexchange.projections;
import java.math.BigDecimal;
public interface RealEstateDetailProjection {
    String getDescription();

    BigDecimal getPriceMin();

    String getPriceTime();

    Integer getTotalFloors();

    Integer getBath();

    Double getWallArea();

    String getLandscapeView();

    Integer getDirection();

    Integer getBalcony();   

    Integer getFurnitureType();

    Integer getFurnitureStatus();

    Integer getPriceRent();

    Double getReturnRate();

    Integer getLegalDoc();

    Integer getWidthY();

    Integer getLongX();

    Integer getStreetHouse();

    Integer getFSBO();

    String getShape();

    Integer getDistance2Facade();

    Integer getAdjacentFacadeNum();

    String getAdjacentRoad();

    Integer getAlleyMinWidth();

    Integer getAdjacentAlleyMinWidth();

    String getStructure();

    Integer getDTSXD();

    Integer getCtxdPrice();

    Integer getCtxdValue();

}
