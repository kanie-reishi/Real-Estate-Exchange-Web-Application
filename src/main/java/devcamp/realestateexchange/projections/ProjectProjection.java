package devcamp.realestateexchange.projections;

import java.util.Date;
import java.util.List;

// Projection for Project. This projection is used to get the project with its master layouts, utilities, project construction contractors, project design units, project investors, and region links from the database.
public interface ProjectProjection {
    Integer getId();
    String getName();
    String getSlogan();
    String getDescription();
    String getAddress();
    Double getAcreage();
    Double getConstructArea();
    Integer getNumBlock();
    String getNumFloors();
    Integer getNumApartment();
    String getApartmentArea();
    Date getCreatedAt();
    Date getUpdatedAt();
    List<MasterLayoutProjection> getMasterLayouts();
    List<UtilitiesProjection> getUtilities();
    List<ProjectContractorProjection> getProjectConstructionContractors();
    List<ProjectDesignUnitProjection> getProjectDesignUnits();
    List<ProjectInvestorProjection> getProjectInvestors();
    List<RegionLinkProjection> getRegionLinks();
}
