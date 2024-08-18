package devcamp.realestateexchange.event;
import devcamp.realestateexchange.entity.realestate.RealEstate;
public class RealEstateChangedEvent {
    private RealEstate entity;

    public RealEstateChangedEvent(RealEstate entity) {
        this.entity = entity;
    }

    public RealEstate getEntity() {
        return entity;
    }
}
