package devcamp.realestateexchange.projections;


// Projection for Province. This projection is used to get the province with its id and name from the database.
public interface ProvinceProjection {
    Integer getId();
    String getName();
}
