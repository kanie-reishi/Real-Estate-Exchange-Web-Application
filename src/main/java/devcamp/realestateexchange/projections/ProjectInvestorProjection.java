package devcamp.realestateexchange.projections;

// Projection for ProjectInvestor. This projection is used to get the project investor with its investor from the database.
public interface ProjectInvestorProjection {
    Integer getId();
    InvestorProjection getInvestor();
}
