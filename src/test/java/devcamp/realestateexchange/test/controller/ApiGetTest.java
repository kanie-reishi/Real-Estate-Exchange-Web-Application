package devcamp.realestateexchange.test.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiGetTest {

    private static final Logger logger = LoggerFactory.getLogger(ApiGetTest.class);
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Any setup needed before each test
    }
    // Test case for GET APIs

    // Test case for GET /realestate
    @Test
    public void testGetAllRealEstateDtos() throws Exception {
        MvcResult result = mockMvc.perform(get("/realestate")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // Lấy ra kết quả trả về từ API
        String content = result.getResponse().getContentAsString();
        logger.debug("Response: " + content);
        // Additional assertions can be added here if needed
    }

    // Test case for GET /realestate/{id}
    @Test
    public void testGetRealEstateById() throws Exception {
        MvcResult result = mockMvc.perform(get("/realestate/1/detail")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Additional assertions can be added here if needed
    }

    // Test case for GET /project
    @Test
    public void testGetAllProjects() throws Exception {
        MvcResult result = mockMvc.perform(get("/projects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // Lấy ra kết quả trả về từ API
        String content = result.getResponse().getContentAsString();
        logger.debug("Response: " + content);
        // Additional assertions can be added here if needed
    }

    // Test case for GET /project/{id}
    @Test
    public void testGetProjectById() throws Exception {
        MvcResult result = mockMvc.perform(get("/projects/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Additional assertions can be added here if needed
    }

    // Test case for GET /provinces
    @Test
    public void testGetAllProvinces() throws Exception {
        MvcResult result = mockMvc.perform(get("/provinces")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Additional assertions can be added here if needed
    }

    // Test case for GET /districts
    @Test
    public void testGetAllDistricts() throws Exception {
        MvcResult result = mockMvc.perform(get("/districts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Additional assertions can be added here if needed
    }
    // Add more tests for other GET APIs
}