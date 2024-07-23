package devcamp.realestateexchange.dto.user;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Integer id;
    private String fullName;
    private String contactTitle;
    private String phone;
    private String email;
    private String avatarUrl;
    public CustomerDto(Integer id, String fullName, String avatarUrl) {
        this.id = id;
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
    }
}
