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
    public MasterLayoutDto getMasterLayoutById(Integer id) {
        MasterLayout masterLayout = masterLayoutRepository.getMasterLayoutById(id);
        return modelMapper.map(masterLayout, MasterLayoutDto.class);
    }
    public Page<MasterLayoutDto> getMasterLayouts(Pageable pageable) {
        Page<MasterLayout> masterLayouts = masterLayoutRepository.findAll(pageable);
        return masterLayouts.map(masterLayout -> modelMapper.map(masterLayout, MasterLayoutDto.class));
    }
    public MasterLayout saveMasterLayout(MasterLayoutDto masterLayoutDto) {
        MasterLayout masterLayout = modelMapper.map(masterLayoutDto, MasterLayout.class);
        return masterLayoutRepository.save(masterLayout);
    }
    public MasterLayoutDto convertToDto(MasterLayout masterLayout) {
        return modelMapper.map(masterLayout, MasterLayoutDto.class);
    }
    public MasterLayout convertToEntity(MasterLayoutDto masterLayoutDto) {
        return modelMapper.map(masterLayoutDto, MasterLayout.class);
    }
    public void deleteMasterLayout(Integer id) {
        masterLayoutRepository.deleteById(id);
    }
}
