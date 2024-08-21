package devcamp.realestateexchange.projections;

import devcamp.realestateexchange.entity.realestate.ConstructionContractor;

public interface ProjectContractorProjection {
    Integer getId();
    ContractorProjection getContractor();
}
