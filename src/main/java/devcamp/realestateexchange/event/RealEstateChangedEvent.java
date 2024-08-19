package devcamp.realestateexchange.event;
import devcamp.realestateexchange.dto.realestate.RealEstateDto;
public class RealEstateChangedEvent {
    private RealEstateDto dto;

    public RealEstateChangedEvent(RealEstateDto dto) {
        this.dto = dto;
    }

    public RealEstateDto getDto() {
        return dto;
    }
}
