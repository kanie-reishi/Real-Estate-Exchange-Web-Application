package devcamp.realestateexchange.services.realestate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.realestate.DesignUnitDto;
import devcamp.realestateexchange.entity.realestate.DesignUnit;
import devcamp.realestateexchange.projections.DesignUnitProjection;
import devcamp.realestateexchange.repositories.realestate.IDesignUnitRepository;

@Service
public class DesignUnitService {
    @Autowired
    private IDesignUnitRepository designUnitRepository;
    
    // Get design unit by id
    public DesignUnitDto getDesignUnitById(Integer id) {
        DesignUnitProjection designUnitProjection = designUnitRepository.getDesignUnitById(id);
        DesignUnitDto designUnitDto = convertProjectionToDto(designUnitProjection);
        return designUnitDto;
    }
    // Get all design unit
    public Page<DesignUnitDto> getAllDesignUnit(Pageable pageable) {
        Page<DesignUnitProjection> designUnitProjections = designUnitRepository.findAllProjections(pageable);
        return designUnitProjections.map(designUnitProjection -> convertProjectionToDto(designUnitProjection));
    }
    // Get design unit by project id
    public List<DesignUnitDto> getDesignUnitByProjectId(Integer projectId) {
        List<DesignUnitProjection> designUnitProjections = designUnitRepository.findDesignUnitByProjectId(projectId);
        return designUnitProjections.stream().map(designUnitProjection -> convertProjectionToDto(designUnitProjection)).toList();
    }
    // Save design unit
    public DesignUnit saveDesignUnit(DesignUnitDto designUnitDto) {
        DesignUnit designUnit = convertDtoToEntity(designUnitDto);
        return designUnitRepository.save(designUnit);
    }
    // Convert Projection to Dto
    public DesignUnitDto convertProjectionToDto(DesignUnitProjection designUnitProjection) {
        DesignUnitDto designUnitDto = new DesignUnitDto();
        designUnitDto.setId(designUnitProjection.getId());
        designUnitDto.setName(designUnitProjection.getName());
        designUnitDto.setDescription(designUnitProjection.getDescription());
        designUnitDto.setAddress(designUnitProjection.getAddress());
        designUnitDto.setPhone(designUnitProjection.getPhone());
        designUnitDto.setPhone2(designUnitProjection.getPhone2());
        designUnitDto.setFax(designUnitProjection.getFax());
        designUnitDto.setEmail(designUnitProjection.getEmail());
        designUnitDto.setWebsite(designUnitProjection.getWebsite());
        designUnitDto.setNote(designUnitProjection.getNote());
        return designUnitDto;
    }
    // Convert Dto to Entity
    public DesignUnit convertDtoToEntity(DesignUnitDto designUnitDto) {
        DesignUnit designUnit = new DesignUnit();
        designUnit.setId(designUnitDto.getId());
        designUnit.setName(designUnitDto.getName());
        designUnit.setDescription(designUnitDto.getDescription());
        designUnit.setAddress(designUnitDto.getAddress());
        designUnit.setPhone(designUnitDto.getPhone());
        designUnit.setPhone2(designUnitDto.getPhone2());
        designUnit.setFax(designUnitDto.getFax());
        designUnit.setEmail(designUnitDto.getEmail());
        designUnit.setWebsite(designUnitDto.getWebsite());
        designUnit.setNote(designUnitDto.getNote());
        return designUnit;
    }

    public void deleteDesignUnit(Integer id) {
        designUnitRepository.deleteById(id);
    }
}
