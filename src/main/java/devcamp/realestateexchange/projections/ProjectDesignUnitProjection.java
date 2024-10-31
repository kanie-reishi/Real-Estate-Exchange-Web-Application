package devcamp.realestateexchange.projections;

// Projection for ProjectDesignUnit. This projection is used to get the project design unit with its design unit from the database.
public interface ProjectDesignUnitProjection {
    Integer getId();
    DesignUnitProjection getDesignUnit();
}
