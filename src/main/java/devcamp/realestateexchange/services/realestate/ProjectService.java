package devcamp.realestateexchange.services.realestate;

import java.text.SimpleDateFormat;

import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.realestate.ProjectDto;
import devcamp.realestateexchange.event.RealEstateChangedEventHandler;
import devcamp.realestateexchange.projections.ProjectProjection;
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

    public Page<ProjectDto> getAllsProjectDto(Pageable pageable) {
        Page<ProjectProjection> projectProjections = projectRepository.findAllProjections(pageable);

        return projectProjections.map(this::convertProjectionToDto);

    }
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
        projectDto.setCreatedAt(projectProjection.getCreatedAt());
        return projectDto;
    }
}
