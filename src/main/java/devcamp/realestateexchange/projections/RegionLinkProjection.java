package devcamp.realestateexchange.projections;

// Projection for RegionLink. This projection is used to get the region link with its region from the database.
public interface RegionLinkProjection {
    Integer getId();
    String getName();
    String getDescription();
    String getAddress();
    Double getLatitude();
    Double getLongitude();
}
