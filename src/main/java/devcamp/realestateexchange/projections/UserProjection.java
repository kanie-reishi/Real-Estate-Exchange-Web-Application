package devcamp.realestateexchange.projections;


// Projection for User. This projection is used to get the user with its id and username from the database.
public interface UserProjection {
    Integer getId();
    String getUsername();
}
