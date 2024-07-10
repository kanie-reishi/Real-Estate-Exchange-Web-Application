package devcamp.realestateexchange.dto.authentication;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LoginRequest {
    private String username;

    @NotBlank
	private String password;
    private String email;
    private String phone;
}
