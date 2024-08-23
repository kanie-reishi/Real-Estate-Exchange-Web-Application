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

}