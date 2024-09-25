package devcamp.realestateexchange.services.realestate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.realestate.MasterLayoutDto;
import devcamp.realestateexchange.entity.realestate.MasterLayout;
import devcamp.realestateexchange.repositories.realestate.IMasterLayoutRepository;

@Service
public class MasterLayoutService {
    @Autowired
    private IMasterLayoutRepository masterLayoutRepository;
    @Autowired
    private ModelMapper modelMapper;
    // Get master layout by id
    public MasterLayoutDto getMasterLayoutById(Integer id) {
        MasterLayout masterLayout = masterLayoutRepository.getMasterLayoutById(id);
        return modelMapper.map(masterLayout, MasterLayoutDto.class);
    }
    // Get all master layouts
    public Page<MasterLayoutDto> getMasterLayouts(Pageable pageable) {
        Page<MasterLayout> masterLayouts = masterLayoutRepository.findAll(pageable);
        return masterLayouts.map(masterLayout -> modelMapper.map(masterLayout, MasterLayoutDto.class));
    }
    // Save master layout
    public MasterLayout saveMasterLayout(MasterLayoutDto masterLayoutDto) {
        MasterLayout masterLayout = modelMapper.map(masterLayoutDto, MasterLayout.class);
        return masterLayoutRepository.save(masterLayout);
    }
    // Convert Projection to Dto
    public MasterLayoutDto convertProjectionToDto(MasterLayout masterLayout) {
        MasterLayoutDto masterLayoutDto = new MasterLayoutDto();
        masterLayoutDto.setId(masterLayout.getId());
        masterLayoutDto.setName(masterLayout.getName());
        masterLayoutDto.setDescription(masterLayout.getDescription());
        masterLayoutDto.setAcreage(masterLayout.getAcreage());
        masterLayoutDto.setApartmentList(masterLayout.getApartmentList());
        return masterLayoutDto;
    }
    // Convert Dto to Entity
    public MasterLayoutDto convertToDto(MasterLayout masterLayout) {
        return modelMapper.map(masterLayout, MasterLayoutDto.class);
    }
    // Convert to Entity
    public MasterLayout convertToEntity(MasterLayoutDto masterLayoutDto) {
        return modelMapper.map(masterLayoutDto, MasterLayout.class);
    }
    // Delete master layout
    public void deleteMasterLayout(Integer id) {
        masterLayoutRepository.deleteById(id);
    }
}
