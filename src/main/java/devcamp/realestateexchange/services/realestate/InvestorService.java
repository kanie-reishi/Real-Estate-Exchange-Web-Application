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
    public InvestorDto getInvestorById(Integer id) {
        InvestorProjection investorProjection = investorRepository.getInvestorById(id);
        return modelMapper.map(investorProjection, InvestorDto.class);
    }
    public Page<InvestorDto> getInvestors(Pageable pageable) {
        Page<InvestorProjection> investorProjections = investorRepository.findAllProjections(pageable);
        return investorProjections.map(investorProjection -> modelMapper.map(investorProjection, InvestorDto.class));
    }
    public List<InvestorDto> getInvestorsByProjectId(Integer projectId) {
        List<InvestorProjection> investorProjections = investorRepository.findInvestorByProjectId(projectId);
        return investorProjections.stream().map(investorProjection -> modelMapper.map(investorProjection, InvestorDto.class)).toList();
    }
    public Investor saveInvestor(InvestorDto investorDto) {
        Investor investor = modelMapper.map(investorDto, Investor.class);
        return investorRepository.save(investor);
    }
    public InvestorDto convertToDto(Investor investor) {
        return modelMapper.map(investor, InvestorDto.class);
    }
    public Investor convertToEntity(InvestorDto investorDto) {
        return modelMapper.map(investorDto, Investor.class);
    }
    public void deleteInvestor(Integer id) {
        investorRepository.deleteById(id);
    }
}
