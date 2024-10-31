package devcamp.realestateexchange.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// ProjectSearchParameters model class. This class is used to store the project search parameters.
public class ProjectSearchParameters extends SearchParameters {
    private String name;
    private Integer provinceId;
    private Integer districtId;
    private Integer wardId;
    private Integer streetId;
    private String address;
    private String description;
    private Double acreage;
    private Double constructArea;
    private Integer numBlock;
    private String numFloors;
    private Integer numApartment;
    private String apartmentArea;
    private String createdAt;
    private String updatedAt;
    private String slogan;
    private String investor;
    private String contractor;
    private String masterLayout;
    private String realEstate;
    private String utilities;
    private String regionLinks;
    private String designUnit;
}
