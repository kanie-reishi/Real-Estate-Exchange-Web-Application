package devcamp.realestateexchange.projections;

// Projection for UserReference. This projection is used to get the user reference with its createdBy and updatedBy from the database.
public interface UserReferenceProjection {
    UserProjection getCreatedBy();
    UserProjection getUpdatedBy();
}
