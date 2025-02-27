package devcamp.realestateexchange.services.realestate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import devcamp.realestateexchange.dto.realestate.ContractorDto;
import devcamp.realestateexchange.dto.realestate.DesignUnitDto;
import devcamp.realestateexchange.dto.realestate.InvestorDto;
import devcamp.realestateexchange.dto.realestate.MasterLayoutDto;
import devcamp.realestateexchange.dto.realestate.ProjectDto;
import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.dto.realestate.RegionLinkDto;
import devcamp.realestateexchange.dto.realestate.UtilitiesDto;
import devcamp.realestateexchange.entity.realestate.ConstructionContractor;
import devcamp.realestateexchange.entity.realestate.DesignUnit;
import devcamp.realestateexchange.entity.realestate.Investor;
import devcamp.realestateexchange.entity.realestate.MasterLayout;
import devcamp.realestateexchange.entity.realestate.Project;
import devcamp.realestateexchange.entity.realestate.ProjectConstructionContractor;
import devcamp.realestateexchange.entity.realestate.ProjectDesignUnit;
import devcamp.realestateexchange.entity.realestate.ProjectInvestor;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.entity.realestate.RegionLink;
import devcamp.realestateexchange.entity.realestate.Utilities;
import devcamp.realestateexchange.event.ProjectChangedEvent;
import devcamp.realestateexchange.event.RealEstateChangedEventHandler;
import devcamp.realestateexchange.models.ProjectSearchParameters;
import devcamp.realestateexchange.projections.MasterLayoutProjection;
import devcamp.realestateexchange.projections.ProjectContractorProjection;
import devcamp.realestateexchange.projections.ProjectDesignUnitProjection;
import devcamp.realestateexchange.projections.ProjectInvestorProjection;
import devcamp.realestateexchange.projections.ProjectProjection;
import devcamp.realestateexchange.projections.RealEstateProjection;
import devcamp.realestateexchange.projections.RegionLinkProjection;
import devcamp.realestateexchange.projections.UtilitiesProjection;
import devcamp.realestateexchange.repositories.location.IDistrictRepository;
import devcamp.realestateexchange.repositories.location.IProvinceRepository;
import devcamp.realestateexchange.repositories.location.IStreetRepository;
import devcamp.realestateexchange.repositories.location.IWardRepository;
import devcamp.realestateexchange.repositories.realestate.IProjectRepository;
import devcamp.realestateexchange.services.media.PhotoService;

@Service
public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(RealEstateChangedEventHandler.class);

    private static final SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    @Autowired
    private IProjectRepository projectRepository;
    @Autowired
    private IProvinceRepository provinceRepository;
    @Autowired
    private IDistrictRepository districtRepository;
    @Autowired
    private IWardRepository wardRepository;
    @Autowired
    private IStreetRepository streetRepository;
    @Autowired
    PhotoService photoService;
    @Autowired
    private RestClient client;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    // Get all project DTO method
    public Page<ProjectDto> getAllsProjectDto(Pageable pageable) {
        Page<ProjectProjection> projectProjections = projectRepository.findAllProjections(pageable);

        return projectProjections.map(this::convertProjectionToDto);

    }

    // Convert projection to DTO method
    public ProjectDto convertProjectionToDto(ProjectProjection projectProjection) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(projectProjection.getId());
        projectDto.setName(projectProjection.getName());
        projectDto.setSlogan(projectProjection.getSlogan());
        projectDto.setDescription(projectProjection.getDescription());
        projectDto.setAcreage(projectProjection.getAcreage());
        projectDto.setConstructArea(projectProjection.getConstructArea());
        projectDto.setNumBlock(projectProjection.getNumBlock());
        projectDto.setNumFloors(projectProjection.getNumFloors());
        projectDto.setNumApartment(projectProjection.getNumApartment());
        projectDto.setApartmentArea(projectProjection.getApartmentArea());
        if (projectProjection.getCreatedAt() != null) {
            projectDto.setCreatedAt(isoFormat.format(projectProjection.getCreatedAt()));
        }
        if (projectProjection.getUpdatedAt() != null) {
            projectDto.setUpdatedAt(isoFormat.format(projectProjection.getUpdatedAt()));
        }
        // Convert contractors to DTOs
        for (ProjectContractorProjection projectContractorProjection : projectProjection
                .getProjectConstructionContractors()) {
            ContractorDto contractorDto = new ContractorDto();
            contractorDto.setId(projectContractorProjection.getContractor().getId());
            contractorDto.setName(projectContractorProjection.getContractor().getName());
            if (projectDto.getContractors() == null) {
                projectDto.setContractors(new ArrayList<>());
            }
            projectDto.getContractors().add(contractorDto);
        }
        // Convert investors to DTOs
        for (ProjectInvestorProjection projectInvestorProjection : projectProjection.getProjectInvestors()) {
            InvestorDto investorDto = new InvestorDto();
            investorDto.setId(projectInvestorProjection.getInvestor().getId());
            investorDto.setName(projectInvestorProjection.getInvestor().getName());
            if (projectDto.getInvestors() == null) {
                projectDto.setInvestors(new ArrayList<>());
            }
            projectDto.getInvestors().add(investorDto);
        }
        // Convert design units to DTOs
        for (ProjectDesignUnitProjection projectDesignUnitProjection : projectProjection.getProjectDesignUnits()) {
            DesignUnitDto designUnitDto = new DesignUnitDto();
            designUnitDto.setId(projectDesignUnitProjection.getDesignUnit().getId());
            designUnitDto.setName(projectDesignUnitProjection.getDesignUnit().getName());
            if (projectDto.getDesignUnits() == null) {
                projectDto.setDesignUnits(new ArrayList<>());
            }
            projectDto.getDesignUnits().add(designUnitDto);
        }
        // Convert utilities to DTOs
        for (UtilitiesProjection utilities : projectProjection.getUtilities()) {
            UtilitiesDto utilitiesDto = new UtilitiesDto();
            utilitiesDto.setId(utilities.getId());
            utilitiesDto.setName(utilities.getName());
            if (projectDto.getUtilities() == null) {
                projectDto.setUtilities(new ArrayList<>());
            }
            projectDto.getUtilities().add(utilitiesDto);
        }
        // Convert region links to DTOs
        for (RegionLinkProjection regionLink : projectProjection.getRegionLinks()) {
            RegionLinkDto regionLinkDto = new RegionLinkDto();
            regionLinkDto.setId(regionLink.getId());
            regionLinkDto.setName(regionLink.getName());
            if (projectDto.getRegionLinks() == null) {
                projectDto.setRegionLinks(new ArrayList<>());
            }
            projectDto.getRegionLinks().add(regionLinkDto);
        }
        // Convert master layouts to DTOs
        for (MasterLayoutProjection masterLayout : projectProjection.getMasterLayouts()) {
            MasterLayoutDto masterLayoutDto = new MasterLayoutDto();
            masterLayoutDto.setId(masterLayout.getId());
            masterLayoutDto.setName(masterLayout.getName());
            if (projectDto.getMasterLayouts() == null) {
                projectDto.setMasterLayouts(new ArrayList<>());
            }
            projectDto.getMasterLayouts().add(masterLayoutDto);
        }
        return projectDto;
    }

    // Get project by id method
    public ProjectDto getProjectById(Integer id) {
        ProjectProjection projectProjection = projectRepository.getProjectById(id);
        return convertProjectionToDto(projectProjection);
    }

    // Index all project method
    public void indexAllProject() {
        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            ProjectDto projectDto = convertEntityToSearchDto(project);
            eventPublisher.publishEvent(new ProjectChangedEvent(projectDto));
        }
    }

    // Index test project method
    public void indexTestProject() {
        Project project = projectRepository.findById(1).get();
        ProjectDto projectDto = convertEntityToSearchDto(project);
        eventPublisher.publishEvent(new ProjectChangedEvent(projectDto));
    }

    // Search project method
    public Page<ProjectDto> search(ProjectSearchParameters searchParameters) {
        List<ProjectDto> projectDtos = new ArrayList<>();
        try {
            Request request = new Request("POST", "/project_index/_search");
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            if (searchParameters.getSize() != null) {
                rootNode.put("size", searchParameters.getSize());
            }
            if (searchParameters.getFrom() != null) {
                rootNode.put("from", searchParameters.getFrom());
            }
            // Add the query to the root node
            ObjectNode queryNode = rootNode.putObject("query");
            ObjectNode boolNode = queryNode.putObject("bool");
            // Add the must clause to the bool node
            ArrayNode mustNode = boolNode.putArray("must");
            // Add the multi_match query to the must node
            if (searchParameters.getSearchText() != null) {
                // Add the multi_match query to the must node
                ObjectNode multiMatchNode = mustNode.addObject().putObject("multi_match");
                String text = searchParameters.getSearchText();
                // Add text to the multi_match node
                multiMatchNode.put("query", text);
                // Add analyzer
                multiMatchNode.put("analyzer", "vietnamese_analyzer");
                // Add fields
                multiMatchNode.putArray("fields")
                        .add("name")
                        .add("description")
                        .add("investor.name")
                        .add("contractor.name")
                        .add("masterLayout.name")
                        .add("realEstates.title")
                        .add("utilities.name")
                        .add("regionLinks.name")
                        .add("designUnits.name");
                // Add search type
                multiMatchNode.put("type", "best_fields");
                // Add fuzziness
                multiMatchNode.put("fuzziness", "AUTO");
            }
            // Add the filter clause to the bool node

            // Add sort to the root node
            if (searchParameters.getSort() != null) {
                ObjectNode sortNode = rootNode.putObject("sort");
                ObjectNode sortFieldNode = sortNode.putObject(searchParameters.getSort());
                sortFieldNode.put("order", searchParameters.getOrder());
            }

            // Convert the JSON object to a string
            String json = objectMapper.writeValueAsString(rootNode);
            logger.info("JSON: {}", json);

            // Set the JSON entity of the request
            request.setJsonEntity(json);

            // Execute the request
            Response response = client.performRequest(request);
            // Parse the response
            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject responseJson = new JSONObject(responseBody);
            JSONArray hits = responseJson.getJSONObject("hits").getJSONArray("hits");
            // Get the total number of hits
            int total = responseJson.getJSONObject("hits").getJSONObject("total").getInt("value");
            // Convert the hits to project DTOs
            for (int i = 0; i < hits.length(); i++) {
                JSONObject hit = hits.getJSONObject(i);
                JSONObject source = hit.getJSONObject("_source");
                ProjectDto projectDto = objectMapper.readValue(source.toString(), ProjectDto.class);
                projectDtos.add(projectDto);
            }
            // Create a Page object from the result list
            int start = searchParameters.getFrom() != null ? searchParameters.getFrom() : 0;
            int size = searchParameters.getSize() != null ? searchParameters.getSize() : 10;
            if (size == 0) {
                return new PageImpl<>(projectDtos);
            }

            Pageable pageable = PageRequest.of(start / size, size);

            Page<ProjectDto> projectPage = new PageImpl<>(projectDtos, pageable, total);
            // Return the page
            return projectPage;

        } catch (Exception e) {
            logger.error("Error searching project data", e);
            return new PageImpl<>(projectDtos);
        }
    }

    // Convert entity to search DTO method
    public ProjectDto convertEntityToSearchDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(project.getId());
        projectDto.setName(project.getName());
        projectDto.setSlogan(project.getSlogan());
        projectDto.setDescription(project.getDescription());
        projectDto.setAcreage(project.getAcreage());
        projectDto.setConstructArea(project.getConstructArea());
        projectDto.setNumBlock(project.getNumBlock());
        projectDto.setNumFloors(project.getNumFloors());
        projectDto.setNumApartment(project.getNumApartment());
        projectDto.setApartmentArea(project.getApartmentArea());
        if (project.getCreatedAt() != null) {
            projectDto.setCreatedAt(isoFormat.format(project.getCreatedAt()));
        }
        if (project.getUpdatedAt() != null) {
            projectDto.setUpdatedAt(isoFormat.format(project.getUpdatedAt()));
        }
        // Convert contractors to DTOs
        List<ContractorDto> contractors = new ArrayList<>();
        for (ProjectConstructionContractor pcc : project.getProjectConstructionContractors()) {
            ConstructionContractor contractor = pcc.getConstructionContractor();
            ContractorDto contractorDto = new ContractorDto();
            contractorDto.setId(contractor.getId());
            contractorDto.setName(contractor.getName());
            contractors.add(contractorDto);
        }
        projectDto.setContractors(contractors);
        // Convert investors to DTOs
        List<InvestorDto> investors = new ArrayList<>();
        for (ProjectInvestor pi : project.getProjectInvestors()) {
            Investor investor = pi.getInvestor();
            InvestorDto investorDto = new InvestorDto();
            investorDto.setId(investor.getId());
            investorDto.setName(investor.getName());
            investors.add(investorDto);
        }
        projectDto.setInvestors(investors); 
        // Convert design units to DTOs
        List<DesignUnitDto> designUnits = new ArrayList<>();
        for (ProjectDesignUnit pdu : project.getProjectDesignUnits()) {
            DesignUnit designUnit = pdu.getDesignUnit();
            DesignUnitDto designUnitDto = new DesignUnitDto();
            designUnitDto.setId(designUnit.getId());
            designUnitDto.setName(designUnit.getName());    
            designUnits.add(designUnitDto);    
        }
        projectDto.setDesignUnits(designUnits);
        // Convert real estates to DTOs
        List<RealEstateDto> realEstates = new ArrayList<>();
        for (RealEstate realEstate : project.getRealEstates()) {
            RealEstateDto realEstateDto = new RealEstateDto();
            realEstateDto.setId(realEstate.getId());
            realEstateDto.setTitle(realEstate.getTitle());
            realEstates.add(realEstateDto); 
        }
        projectDto.setRealEstates(realEstates);
        // Convert utilities to DTOs
        List<UtilitiesDto> utilitiesList = new ArrayList<>();
        for (Utilities utilities : project.getUtilities()) {
            UtilitiesDto utilitiesDto = new UtilitiesDto();
            utilitiesDto.setId(utilities.getId());
            utilitiesDto.setName(utilities.getName());
            utilitiesList.add(utilitiesDto);
        }
        projectDto.setUtilities(utilitiesList);
        // Convert region links to DTOs
        List<RegionLinkDto> regionLinks = new ArrayList<>();
        for (RegionLink regionLink : project.getRegionLinks()) {
            RegionLinkDto regionLinkDto = new RegionLinkDto();
            regionLinkDto.setId(regionLink.getId());
            regionLinkDto.setName(regionLink.getName());
            regionLinks.add(regionLinkDto);
        }
        projectDto.setRegionLinks(regionLinks);
        // Convert master layouts to DTOs
        List<MasterLayoutDto> masterLayouts = new ArrayList<>();
        for (MasterLayout masterLayout : project.getMasterLayouts()) {
            MasterLayoutDto masterLayoutDto = new MasterLayoutDto();
            masterLayoutDto.setId(masterLayout.getId());
            masterLayoutDto.setName(masterLayout.getName());
            masterLayouts.add(masterLayoutDto);
        }
        projectDto.setMasterLayouts(masterLayouts);
        return projectDto;
    }
}