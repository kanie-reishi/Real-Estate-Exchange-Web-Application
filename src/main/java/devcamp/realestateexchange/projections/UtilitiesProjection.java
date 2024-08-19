package devcamp.realestateexchange.projections;
import java.util.Date;
public interface UtilitiesProjection {
    Integer getId();
    String getName();
    String getDescription();
    String getUnit();
    Double getPrice();
    Date getCreatedAt();
    Date getUpdatedAt();
}
