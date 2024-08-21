package devcamp.realestateexchange.services.realestate;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.realestate.RegionLinkDto;
import devcamp.realestateexchange.entity.realestate.RegionLink;
import devcamp.realestateexchange.projections.RegionLinkProjection;
import devcamp.realestateexchange.repositories.realestate.IRegionLinkRepository;

@Service
public class RegionLinkService {
    @Autowired
    private IRegionLinkRepository regionLinkRepository;
    @Autowired
    private ModelMapper modelMapper;

    public RegionLinkDto getRegionLinkById(Integer id) {
        RegionLinkProjection regionLinkProjection = regionLinkRepository.getRegionLinkById(id);
        return modelMapper.map(regionLinkProjection, RegionLinkDto.class);
    }

    public Page<RegionLinkDto> getRegionLinks(Pageable pageable) {
        Page<RegionLinkProjection> regionLinkProjections = regionLinkRepository.findAllProjections(pageable);
        return regionLinkProjections.map(regionLinkProjection -> modelMapper.map(regionLinkProjection, RegionLinkDto.class));
    }

    public List<RegionLinkDto> getRegionLinksByProjectId(Integer projectId) {
        List<RegionLinkProjection> regionLinkProjections = regionLinkRepository.findRegionLinkByProjectId(projectId);
        return regionLinkProjections.stream().map(regionLinkProjection -> modelMapper.map(regionLinkProjection, RegionLinkDto.class)).toList();
    }

    public RegionLink saveRegionLink(RegionLinkDto regionLinkDto) {
        RegionLink regionLink = modelMapper.map(regionLinkDto, RegionLink.class);
        return regionLinkRepository.save(regionLink);
    }

    public RegionLinkDto convertToDto(RegionLink regionLink) {
        return modelMapper.map(regionLink, RegionLinkDto.class);
    }

    public RegionLink convertToEntity(RegionLinkDto regionLinkDto) {
        return modelMapper.map(regionLinkDto, RegionLink.class);
    }

    public void deleteRegionLink(Integer id) {
        regionLinkRepository.deleteById(id);
    }


    
}
