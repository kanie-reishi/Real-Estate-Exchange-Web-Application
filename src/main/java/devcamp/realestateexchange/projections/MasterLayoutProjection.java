package devcamp.realestateexchange.projections;
import java.util.List;
public interface MasterLayoutProjection {
    Integer getId();
    String getName();
    String getDescription();
    Double getAcreage();
    List<String> getApartmentList();
}
