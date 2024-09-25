package devcamp.realestateexchange.services.realestate;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.realestate.ContractorDto;
import devcamp.realestateexchange.entity.realestate.ConstructionContractor;
import devcamp.realestateexchange.projections.ContractorProjection;
import devcamp.realestateexchange.repositories.realestate.IConstructionContractorRepository;

@Service
public class ConstructionContractorService {
    @Autowired
    private IConstructionContractorRepository constructionContractorRepository;
    @Autowired
    private ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(ConstructionContractorService.class.getName());
    // Get ConstructionContractor by id
    public ConstructionContractor getConstructionContractorById(Integer id) {
        return constructionContractorRepository.findById(id).orElse(null);
    }
    // Get all ConstructionContractors
    public Page<ConstructionContractor> getConstructionContractorsAll(Pageable pageable) {
        return constructionContractorRepository.findAll(pageable);
    }
    // Get ConstructionContractors by project id
    public List<ContractorDto> getConstructionContractorsByProjectId(Integer projectId) {
        List<ContractorProjection> unitProjections = constructionContractorRepository.findConstructionContractorByProjectId(projectId);
        return unitProjections.stream().map(unitProjection -> modelMapper.map(unitProjection, ContractorDto.class)).toList();
    }
    // Save ConstructionContractor
    public ConstructionContractor saveConstructionContractor(ContractorDto contractorDto) {
        ConstructionContractor constructionContractor = modelMapper.map(contractorDto, ConstructionContractor.class);
        return constructionContractorRepository.save(constructionContractor);
    }
    // Convert to DTO
    public ContractorDto convertToDto(ConstructionContractor constructionContractor) {
        return modelMapper.map(constructionContractor, ContractorDto.class);
    }
    // Convert to Entity
    public ConstructionContractor convertToEntity(ContractorDto contractorDto) {
        return modelMapper.map(contractorDto, ConstructionContractor.class);
    }
    // Delete ConstructionContractor
    public void deleteConstructionContractor(Integer id) {
        constructionContractorRepository.deleteById(id);
    }
}
