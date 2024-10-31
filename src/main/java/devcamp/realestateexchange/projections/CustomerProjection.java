package devcamp.realestateexchange.projections;
// Projection for Customer. This projection is used to get the customer with its photo from the database.
public interface CustomerProjection {
    Integer getId();
    String getFullName();
    String getPhone();
    String getEmail();
    PhotoUrlProjection getPhoto();
}
