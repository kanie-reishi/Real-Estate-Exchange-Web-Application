package devcamp.realestateexchange.projections;
// Projection for District. This projection is used to get the district with its name and prefix from the database.
public interface DistrictProjection {
    Integer getId();
    String getName();
    String getPrefix();
}
