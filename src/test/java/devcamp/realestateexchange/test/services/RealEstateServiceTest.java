package devcamp.realestateexchange.test.services;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.projections.RealEstateProjection;
import devcamp.realestateexchange.repositories.realestate.IRealEstateRepository;
import devcamp.realestateexchange.services.realestate.RealEstateService;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
public class RealEstateServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(RealEstateServiceTest.class);
    @Mock
    private IRealEstateRepository realEstateRepository;

    @Mock
    private RestClient restClient;

    @InjectMocks
    private RealEstateService realEstateService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllRealEstateDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        RealEstateDto realEstateDto = new RealEstateDto();
        realEstateDto.setId(1);
        realEstateDto.setTitle("Test Real Estate");

        List<RealEstateDto> realEstateDtoList = Collections.singletonList(realEstateDto);
        Page<RealEstateDto> realEstateDtoPage = new PageImpl<>(realEstateDtoList, pageable, realEstateDtoList.size());

        Page<RealEstateProjection> realEstateProjectionPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
        when(realEstateRepository.findAllBasicProjections(any(Pageable.class))).thenReturn(realEstateProjectionPage);
        Page<RealEstateDto> result = realEstateService.getAllRealEstateDtos(pageable, 0);
        logger.debug("pageable: " + pageable);
        logger.debug("Result: " + result.getContent());
        assertEquals(0, result.getTotalElements());
    }

    // Thêm các test case khác cho các phương thức khác trong RealEstateService
}