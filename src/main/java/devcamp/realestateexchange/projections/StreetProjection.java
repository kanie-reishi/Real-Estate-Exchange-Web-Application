package devcamp.realestateexchange.projections;

// Projection for Street. This projection is used to get the street with its prefix from the database.
public interface StreetProjection {
    Integer getId();
    String getName();
    String getPrefix();
}
