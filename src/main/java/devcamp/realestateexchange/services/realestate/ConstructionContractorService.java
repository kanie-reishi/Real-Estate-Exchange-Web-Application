package devcamp.realestateexchange.services.realestate;

import java.util.List;

import org.elasticsearch.common.recycler.Recycler.C;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.realestate.ContractorDto;
import devcamp.realestateexchange.entity.realestate.ConstructionContractor;
import devcamp.realestateexchange.projections.ContractorProjection;
import devcamp.realestateexchange.projections.UnitProjection;
import devcamp.realestateexchange.repositories.realestate.IConstructionContractorRepository;

@Service
public class ConstructionContractorService {
    @Autowired
    private IConstructionContractorRepository constructionContractorRepository;
    @Autowired
    private ModelMapper modelMapper;
    public ContractorDto getConstructionContractorById(Integer id) {
        ContractorProjection unitProjection = constructionContractorRepository.getConstructionContractorById(id);
        return modelMapper.map(unitProjection, ContractorDto.class);
    }
    public Page<ContractorDto> getConstructionContractors(Pageable pageable) {
        Page<ContractorProjection> unitProjections = constructionContractorRepository.findAllProjections(pageable);
        return unitProjections.map(unitProjection -> modelMapper.map(unitProjection, ContractorDto.class));
    }
    public List<ContractorDto> getConstructionContractorsByProjectId(Integer projectId) {
        List<ContractorProjection> unitProjections = constructionContractorRepository.findConstructionContractorByProjectId(projectId);
        return unitProjections.stream().map(unitProjection -> modelMapper.map(unitProjection, ContractorDto.class)).toList();
    }
    public ConstructionContractor saveConstructionContractor(ContractorDto contractorDto) {
        ConstructionContractor constructionContractor = modelMapper.map(contractorDto, ConstructionContractor.class);
        return constructionContractorRepository.save(constructionContractor);
    }
    public ContractorDto convertToDto(ConstructionContractor constructionContractor) {
        return modelMapper.map(constructionContractor, ContractorDto.class);
    }
    public ConstructionContractor convertToEntity(ContractorDto contractorDto) {
        return modelMapper.map(contractorDto, ConstructionContractor.class);
    }
    public void deleteConstructionContractor(Integer id) {
        constructionContractorRepository.deleteById(id);
    }
}
