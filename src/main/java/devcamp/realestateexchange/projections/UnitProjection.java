package devcamp.realestateexchange.projections;

// Projection for Unit. This projection is used to get the unit from the database.
public interface UnitProjection {
    Integer getId();
    String getName();
    String getDescription();
    String getAddress();
    String getPhone();
    String getPhone2();
    String getFax();
    String getEmail();
    String getWebsite();
    String getNote();
}
