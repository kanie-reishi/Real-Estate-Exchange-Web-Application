package devcamp.realestateexchange.projections;
public interface UserReferenceProjection {
    UserProjection getCreatedBy();
    UserProjection getUpdatedBy();
}
