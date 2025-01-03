package devcamp.realestateexchange.test.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import static org.assertj.core.api.Assertions.assertThat;
@WebMvcTest
// FIXME: This test is not working as expected
public class AuthenticationTest {
    @Autowired
    private MockMvc mockMvc;

    // This test is to check if the login endpoint is successfully returning 200
    @Test
    public void loginWithValidUser_ShouldReturn200() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "admin")
                .param("password", "password"))
                .andExpect(status().isOk());
    }

    // This test is to check when the user is not authenticated, the login endpoint
    // should return 401
    @Test
    public void loginWithInvalidUser_ShouldReturn401() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "invalidUser")
                .param("password", "wrongPassword"))
                .andExpect(status().isUnauthorized());
    }

    // This test is to check if the user with the role admin can access the admin
    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    public void accessAdminEndpoint_AsAdmin_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    // This test is to check if the user with the role user can access the admin
    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void accessAdminEndpoint_AsUser_ShouldReturn403() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    // This test is to check SecurityContext
    @Test
    @WithMockUser(username = "testUser", roles = { "USER" })
    public void securityContext_ShouldContainMockUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        assertThat(authentication.getName()).isEqualTo("testUser");
        assertThat(authentication.getAuthorities()).extracting("authority").contains("ROLE_USER");
    }
}
