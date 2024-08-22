package devcamp.realestateexchange.projections;

import java.util.Date;
import java.util.List;

public interface ProjectProjection {
    Integer getId();
    String getName();
    String getSlogan();
    String getDescription();
    Double getAcreage();
    Double getConstructArea();
    Integer getNumBlock();
    String getNumFloors();
    Integer getNumApartment();
    String getApartmentArea();
    Date getCreatedAt();
    Date getUpdatedAt();
    List<MasterLayoutProjection> getMasterLayout();
    List<UtilitiesProjection> getUtilities();
    List<ProjectContractorProjection> getContractor();
    List<ProjectDesignUnitProjection> getDesignUnit();
    List<ProjectInvestorProjection> getInvestor();
    List<RegionLinkProjection> getRegionLink();
    List<RealEstateBasicProjection> getRealEstate();
}
