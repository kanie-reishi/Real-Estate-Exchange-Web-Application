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
    private String contactName;
    private String contactTitle;
    private String phone;
    private String email;
    private String avatarUrl;
    public CustomerDto(Integer id, String contactName, String avatarUrl) {
        this.id = id;
        this.contactName = contactName;
        this.avatarUrl = avatarUrl;
    }
}
