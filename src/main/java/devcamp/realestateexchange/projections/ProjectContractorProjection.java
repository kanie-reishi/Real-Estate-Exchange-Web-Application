package devcamp.realestateexchange.projections;

import devcamp.realestateexchange.entity.realestate.ConstructionContractor;

// Projection for ProjectContractor. This projection is used to get the project contractor with its contractor from the database.
public interface ProjectContractorProjection {
    Integer getId();
    ContractorProjection getContractor();
}
