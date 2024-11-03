package devcamp.realestateexchange.test.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import devcamp.realestateexchange.dto.location.AddressDto;
import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.dto.location.StreetDto;
import devcamp.realestateexchange.dto.location.WardDto;
import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import lombok.With;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RealEstatesControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(RealEstatesControllerTest.class);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

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

    // Test case for POST /realestate + PUT /realestate + DELETE /realestate/{id}
    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    public void testCreateUpdateDeleteRealEstate() throws Exception {
        // Add test case for creating a new real estate
        RealEstateDto realEstateDto = new RealEstateDto();
        realEstateDto.setTitle("Test Real Estate");
        realEstateDto.setPrice(new BigDecimal(1000000));
        realEstateDto.setAddress("123 Test Street");
        realEstateDto.setAcreage(100.00);
        realEstateDto.setBedroom(2);
        realEstateDto.setBath(1);
        realEstateDto.setDescription("Test description");

        // Set address details
        AddressDto addressDto = new AddressDto();
        ProvinceDto provinceDto = new ProvinceDto();
        provinceDto.setId(1);
        provinceDto.setName("Test Province");
        addressDto.setProvince(provinceDto);

        DistrictDto districtDto = new DistrictDto();
        districtDto.setId(1);
        districtDto.setName("Test District");
        addressDto.setDistrict(districtDto);

        WardDto wardDto = new WardDto();
        wardDto.setId(1);
        wardDto.setName("Test Ward");
        addressDto.setWard(wardDto);

        StreetDto streetDto = new StreetDto();
        streetDto.setId(1);
        streetDto.setName("Test Street");
        addressDto.setStreet(streetDto);

        realEstateDto.setAddressDetail(addressDto);

        String json = objectMapper.writeValueAsString(realEstateDto);
        MvcResult result = mockMvc.perform(post("/realestate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andReturn();
        // In ra kết quả trả về từ API
        String content = result.getResponse().getContentAsString();
        logger.debug("Response: " + content);
        mockMvc.perform(post("/realestate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Test Real Estate")))
                .andExpect(jsonPath("$.price", is(1000000)))
                .andExpect(jsonPath("$.address", is("123 Test Street")))
                .andExpect(jsonPath("$.acreage", is(100.00)))
                .andExpect(jsonPath("$.bedroom", is(2)))
                .andExpect(jsonPath("$.bath", is(1)))
                .andExpect(jsonPath("$.description", is("Test description")))
                .andReturn();
        // Lấy ra id của bất động sản vừa tạo
        Integer realEstateId = objectMapper.readValue(content, RealEstateDto.class).getId();
        
        // Update bất động sản vừa tạo
        realEstateDto.setTitle("Test Real Estate Updated");
        realEstateDto.setPrice(new BigDecimal(2000000));
        realEstateDto.setAddress("456 Test Street");
        realEstateDto.setAcreage(200.00);
        realEstateDto.setBedroom(3);
        realEstateDto.setBath(2);
        realEstateDto.setDescription("Test description updated");

        json = objectMapper.writeValueAsString(realEstateDto);
        MvcResult resultPut = mockMvc.perform(put("/realestate/" + realEstateId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Real Estate Updated")))
                .andExpect(jsonPath("$.price", is(2000000)))
                .andExpect(jsonPath("$.address", is("456 Test Street")))
                .andExpect(jsonPath("$.acreage", is(200.00)))
                .andExpect(jsonPath("$.bedroom", is(3)))
                .andExpect(jsonPath("$.bath", is(2)))
                .andExpect(jsonPath("$.description", is("Test description updated")))
                .andReturn();
        String contentPut = resultPut.getResponse().getContentAsString();
        logger.debug("Response: " + contentPut);
        // Xóa bất động sản vừa tạo
        mockMvc.perform(delete("/realestate/" + realEstateId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Kiểm tra xem bất động sản đã bị xóa chưa
        mockMvc.perform(get("/realestate/" + realEstateId + "/detail")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    // Add more tests for other APIs
}