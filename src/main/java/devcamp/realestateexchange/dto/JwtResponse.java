package devcamp.realestateexchange.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import lombok.Builder;
@Getter
@Setter
@Builder
public class JwtResponse {
    private String token;
    private Integer id;
    private String username;
    private String email;
    private String phone;
    private List<String> roles;
    public JwtResponse(String accessToken, Integer id, String username, String email, String phone, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }
    public JwtResponse(String token, Integer id, String username) {
        this.token = token;
        this.id = id;
        this.username = username;
    }
}