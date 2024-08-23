package devcamp.realestateexchange.services.realestate;

import java.util.List;

import org.apache.tomcat.jni.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import devcamp.realestateexchange.dto.location.AddressDto;
import devcamp.realestateexchange.dto.realestate.RegionLinkDto;
import devcamp.realestateexchange.entity.realestate.RegionLink;
import devcamp.realestateexchange.projections.RegionLinkProjection;
import devcamp.realestateexchange.repositories.realestate.IRegionLinkRepository;
import devcamp.realestateexchange.services.media.PhotoService;

@Service
public class RegionLinkService {
    @Autowired
    private IRegionLinkRepository regionLinkRepository;
    @Autowired
    private PhotoService photoService;
    @Autowired
    private ModelMapper modelMapper;
    // Lấy RegionLink theo id
    public RegionLinkDto getRegionLinkById(Integer id) {
        RegionLinkProjection regionLinkProjection = regionLinkRepository.getRegionLinkById(id);
        return convertProjectionToDto(regionLinkProjection);
    }
    // Lấy danh sách RegionLink
    public Page<RegionLinkDto> getRegionLinks(Pageable pageable) {
        Page<RegionLinkProjection> regionLinkProjections = regionLinkRepository.findAllProjections(pageable);
        return regionLinkProjections.map(regionLinkProjection -> convertProjectionToDto(regionLinkProjection));
    }
    // Lấy danh sách RegionLink theo projectId
    public List<RegionLinkDto> getRegionLinksByProjectId(Integer projectId) {
        List<RegionLinkProjection> regionLinkProjections = regionLinkRepository.findRegionLinkByProjectId(projectId);
        return regionLinkProjections.stream().map(regionLinkProjection -> convertProjectionToDto(regionLinkProjection)).toList();
    }
    // Lưu RegionLink
    public RegionLink saveRegionLink(RegionLinkDto regionLinkDto) {
        RegionLink regionLink = modelMapper.map(regionLinkDto, RegionLink.class);
        return regionLinkRepository.save(regionLink);
    }
    // Chuyển từ RegionLinkProjection sang RegionLinkDto
    public RegionLinkDto convertProjectionToDto(RegionLinkProjection regionLinkProjection) {
        RegionLinkDto regionLinkDto = new RegionLinkDto();
        regionLinkDto.setId(regionLinkProjection.getId());
        regionLinkDto.setName(regionLinkProjection.getName());
        regionLinkDto.setDescription(regionLinkProjection.getDescription());
        AddressDto addressDto = new AddressDto();
        addressDto.setLatitude(regionLinkProjection.getLatitude());
        addressDto.setLongitude(regionLinkProjection.getLongitude());
        regionLinkDto.setAddress(addressDto);
        return regionLinkDto;
    }
    // Chuyển từ RegionLink sang RegionLinkDto
    public RegionLinkDto convertToDto(RegionLink regionLink) {
        return modelMapper.map(regionLink, RegionLinkDto.class);
    }
    // Chuyển từ RegionLinkDto sang RegionLink
    public RegionLink convertToEntity(RegionLinkDto regionLinkDto) {
        return modelMapper.map(regionLinkDto, RegionLink.class);
    }
    // Xóa RegionLink
    public void deleteRegionLink(Integer id) {
        regionLinkRepository.deleteById(id);
    }

    // Phương thức thêm photoUrls vào RegionLinkDto
    public void addPhotoUrlsToRegionLinkDto(RegionLinkDto regionLinkDto) {
        List<String> photoUrls = photoService.getPhotoUrlsByRegionLinkId(regionLinkDto.getId());
        regionLinkDto.setPhotoUrls(photoUrls);
    }
    
}
