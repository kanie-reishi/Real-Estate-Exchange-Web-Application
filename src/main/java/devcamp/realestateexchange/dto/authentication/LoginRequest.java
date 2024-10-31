package devcamp.realestateexchange.dto.authentication;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LoginRequest {
    // Tên đăng nhập
    @Size(min = 3, max = 20)
    private String username;

    // Mật khẩu
    @NotBlank
    @Size(min = 6, max = 40)
	private String password;

    // Email
    @Size(max = 50)
    private String email;

    // Số điện thoại
    @Size(max = 20)
    private String phone;
}
