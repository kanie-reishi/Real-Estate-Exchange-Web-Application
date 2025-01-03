package devcamp.realestateexchange.test.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.security.crypto.password.PasswordEncoder;

import devcamp.realestateexchange.controller.AuthController;
import devcamp.realestateexchange.security.jwt.JwtUtils;
import devcamp.realestateexchange.services.user.CustomerService;
import devcamp.realestateexchange.security.services.UserDetailsServiceImpl;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) // Bỏ qua tất cả các bộ lọc bảo mật
public class UserDetailsServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private CustomerService customerService;

    // This test is to check if the created user can login successfully
    // FIXME: This test is not working as expected
    @Test
    @WithMockUser(username = "adminlogin", roles = "ADMIN")
    public void testCustomLoginWithMockedUser() throws Exception {
        UserDetails user = User.builder()
                .username("adminlogin")
                .password(new BCryptPasswordEncoder().encode("adminlogin"))
                .roles("ADMIN")
                .build();
        // Mock the userDetailsService.loadUserByUsername method
        when(userDetailsService.loadUserByUsername("adminlogin")).thenReturn(user);
        // Mock the authenticationManager.authenticate method
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        // Mock the jwtUtils.generateToken method
        String mockedJwtToken = "mocked-jwt-token";
        when(jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn(mockedJwtToken);
        // Mock the passwordEncoder.matches method
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class)))
                .thenReturn(true); // Giả lập rằng mật khẩu luôn đúng

        // JSON request body
        String loginRequestJson = """
                {
                    "username": "adminlogin",
                    "password": "adminlogin"
                }
                """;

        // Thực hiện POST request
        mockMvc.perform(post("/auth/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(cookie().exists("token")) // Kiểm tra cookie "token"
                .andExpect(cookie().value("token", mockedJwtToken)) // Kiểm tra giá trị cookie
                .andExpect(jsonPath("$.message").value("Login successful!")) // Kiểm tra thông điệp
                .andExpect(jsonPath("$.data.token").value(mockedJwtToken)); // Kiểm tra token trong response
    }
}
