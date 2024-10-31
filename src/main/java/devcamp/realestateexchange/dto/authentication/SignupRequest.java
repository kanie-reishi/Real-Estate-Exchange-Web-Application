package devcamp.realestateexchange.dto.authentication;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    // Tên đăng nhập
    @Size(min = 3, max = 20)
    private String username;

    // Email
    @Size(max = 50)
    @Email
    private String email;
    
    // Số điện thoại
    @Size(max = 20)
    private String phone;

    // Mật khẩu
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
