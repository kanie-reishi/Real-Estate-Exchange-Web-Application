package devcamp.realestateexchange.dto.location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String address;
    private ProvinceDto province;
    private DistrictDto district;
    private WardDto ward;
    private StreetDto street;
    private Double latitude;
    private Double longitude;
}
