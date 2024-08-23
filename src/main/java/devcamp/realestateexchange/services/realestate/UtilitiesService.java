package devcamp.realestateexchange.services.realestate;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.realestate.UtilitiesDto;
import devcamp.realestateexchange.entity.realestate.Utilities;
import devcamp.realestateexchange.projections.UtilitiesProjection;
import devcamp.realestateexchange.repositories.realestate.IUtilitiesRepository;

@Service
public class UtilitiesService {
    @Autowired
    private IUtilitiesRepository utilitiesRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UtilitiesDto getUtilityById(Integer id) {
        UtilitiesProjection utilitiesProjection = utilitiesRepository.getUtilitiesById(id);
        return convertProjectionToDto(utilitiesProjection);
    }

    public Page<UtilitiesDto> getUtilities(Pageable pageable) {
        Page<UtilitiesProjection> utilitiesProjections = utilitiesRepository.findAllProjections(pageable);
        return utilitiesProjections.map(utilitiesProjection -> convertProjectionToDto(utilitiesProjection));
    }

    public List<UtilitiesDto> getUtilitiessByProjectId(Integer projectId) {
        List<UtilitiesProjection> utilitiesProjections = utilitiesRepository.findUtilitiesByProjectId(projectId);
        return utilitiesProjections.stream().map(utilitiesProjection -> convertProjectionToDto(utilitiesProjection)).toList();
    }

    public Utilities saveUtility(UtilitiesDto utilitiesDto) {
        Utilities utilities = modelMapper.map(utilitiesDto, Utilities.class);
        return utilitiesRepository.save(utilities);
    }

    public UtilitiesDto convertProjectionToDto(UtilitiesProjection utilitiesProjection) {
        UtilitiesDto utilitiesDto = new UtilitiesDto();
        utilitiesDto.setId(utilitiesProjection.getId());
        utilitiesDto.setName(utilitiesProjection.getName());
        utilitiesDto.setDescription(utilitiesProjection.getDescription());
        return utilitiesDto;
    }
    public UtilitiesDto convertToDto(Utilities utilities) {
        return modelMapper.map(utilities, UtilitiesDto.class);
    }

    public Utilities convertToEntity(UtilitiesDto utilitiesDto) {
        return modelMapper.map(utilitiesDto, Utilities.class);
    }

    public void deleteUtility(Integer id) {
        utilitiesRepository.deleteById(id);
    }

}
