package devcamp.realestateexchange.projections;
import java.util.Date;

// Projection for Utilities. This projection is used to get the utilities with its id, name, description, created at, and updated at from the database.
public interface UtilitiesProjection {
    Integer getId();
    String getName();
    String getDescription();
    Date getCreatedAt();
    Date getUpdatedAt();
}
