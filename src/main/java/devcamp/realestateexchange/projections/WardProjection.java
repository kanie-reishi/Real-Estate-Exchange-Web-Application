package devcamp.realestateexchange.projections;

// Projection for Ward. This projection is used to get the ward with its id, name and prefix from the database.
public interface WardProjection {
    Integer getId();
    String getName();
    String getPrefix();
}
