package devcamp.realestateexchange.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SignupRequest {
    @Size(min = 3, max = 20)
    private String username;

    @Size(max = 50)
    @Email
    private String email;

    @Size(max = 20)
    private String phone;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
