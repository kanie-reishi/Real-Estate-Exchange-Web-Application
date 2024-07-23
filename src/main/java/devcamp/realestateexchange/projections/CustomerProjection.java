package devcamp.realestateexchange.projections;

public interface CustomerProjection {
    Integer getId();
    String getFullName();
    String getPhone();
    String getEmail();
    PhotoProjection getPhoto();
}
