package devcamp.realestateexchange.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import devcamp.realestateexchange.dto.realestate.ContractorDto;
import devcamp.realestateexchange.projections.ContractorProjection;
import devcamp.realestateexchange.repositories.realestate.IConstructionContractorRepository;
import devcamp.realestateexchange.services.realestate.ConstructionContractorService;

public class ContractorMappingTest {

    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
    }

    @Test
    public void testGetConstructionContractorById() {
        // Tạo mock object cho ContractorProjection
        ContractorProjection contractorProjection = mock(ContractorProjection.class);

        // Tạo mock object cho IConstructionContractorRepository
        IConstructionContractorRepository repository = mock(IConstructionContractorRepository.class);
        when(repository.getConstructionContractorById(1)).thenReturn(contractorProjection);

        // Tạo instance của ConstructionContractorService và tiêm các mock object
        ConstructionContractorService service = new ConstructionContractorService();
        ReflectionTestUtils.setField(service, "constructionContractorRepository", repository);
        ReflectionTestUtils.setField(service, "modelMapper", new ModelMapper());

        // Gọi phương thức và kiểm tra kết quả
        ContractorDto contractorDto = service.getConstructionContractorById(1);
        assertNotNull(contractorDto);
        assertEquals((Integer) 0, contractorProjection.getId());
        assertEquals("John Doe", contractorProjection.getName());
        assertEquals("john.doe@example.com", contractorProjection.getEmail());
    }
}