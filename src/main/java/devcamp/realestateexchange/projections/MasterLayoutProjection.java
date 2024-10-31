package devcamp.realestateexchange.projections;
import java.util.List;

// Projection for MasterLayout. This projection is used to get the master layout with its apartments from the database.
public interface MasterLayoutProjection {
    Integer getId();
    String getName();
    String getDescription();
    Double getAcreage();
    List<String> getApartmentList();
}
