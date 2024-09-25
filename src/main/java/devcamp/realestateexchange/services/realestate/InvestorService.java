package devcamp.realestateexchange.services.realestate;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.realestate.InvestorDto;
import devcamp.realestateexchange.entity.realestate.Investor;
import devcamp.realestateexchange.projections.InvestorProjection;
import devcamp.realestateexchange.repositories.realestate.IInvestorRepository;

@Service
public class InvestorService {
    @Autowired
    private IInvestorRepository investorRepository;
    @Autowired
    private ModelMapper modelMapper;
    // Get investor by id
    public InvestorDto getInvestorById(Integer id) {
        InvestorProjection investorProjection = investorRepository.getInvestorById(id);
        return convertProjectionToDto(investorProjection);
    }
    // Get all investors
    public Page<InvestorDto> getInvestors(Pageable pageable) {
        Page<InvestorProjection> investorProjections = investorRepository.findAllProjections(pageable);
        return investorProjections.map(investorProjection -> convertProjectionToDto(investorProjection));
    }
    // Get investor by project id
    public List<InvestorDto> getInvestorsByProjectId(Integer projectId) {
        List<InvestorProjection> investorProjections = investorRepository.findInvestorByProjectId(projectId);
        return investorProjections.stream().map(designUnitProjection -> convertProjectionToDto(designUnitProjection)).toList();
    }
    // Convert Projection to Dto
    public InvestorDto convertProjectionToDto(InvestorProjection investorProjection) {
        InvestorDto investorDto = new InvestorDto();
        investorDto.setId(investorProjection.getId());
        investorDto.setName(investorProjection.getName());
        investorDto.setDescription(investorProjection.getDescription());
        investorDto.setAddress(investorProjection.getAddress());
        investorDto.setPhone(investorProjection.getPhone());
        investorDto.setPhone2(investorProjection.getPhone2());
        investorDto.setFax(investorProjection.getFax());
        investorDto.setEmail(investorProjection.getEmail());
        investorDto.setWebsite(investorProjection.getWebsite());
        investorDto.setNote(investorProjection.getNote());
        return investorDto;
    }
    // Save investor
    public Investor saveInvestor(InvestorDto investorDto) {
        Investor investor = modelMapper.map(investorDto, Investor.class);
        return investorRepository.save(investor);
    }
    // Convert to DTO
    public InvestorDto convertToDto(Investor investor) {
        return modelMapper.map(investor, InvestorDto.class);
    }
    // Convert to Entity
    public Investor convertToEntity(InvestorDto investorDto) {
        return modelMapper.map(investorDto, Investor.class);
    }
    // Delete investor
    public void deleteInvestor(Integer id) {
        investorRepository.deleteById(id);
    }
}
