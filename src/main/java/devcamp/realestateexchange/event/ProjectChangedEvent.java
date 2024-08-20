package devcamp.realestateexchange.event;
import devcamp.realestateexchange.dto.realestate.ProjectDto;
public class ProjectChangedEvent {
    private ProjectDto dto;

    public ProjectChangedEvent(ProjectDto dto) {
        this.dto = dto;
    }
    public ProjectDto getDto() {
        return dto;
    }
}
