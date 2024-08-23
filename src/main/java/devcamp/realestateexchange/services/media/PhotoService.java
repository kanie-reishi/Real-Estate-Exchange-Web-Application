package devcamp.realestateexchange.services.media;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.projections.PhotoUrlProjection;
import devcamp.realestateexchange.repositories.media.IPhotoRepository;
@Service
public class PhotoService {
    @Autowired
    private IPhotoRepository photoRepository;
    public Photo savePhotoMetadata(MultipartFile file, String url){
        Photo photo = new Photo();
        photo.setName(file.getOriginalFilename());
        photo.setUrl(url);
        photo.setSize(file.getSize());
        photo.setType(file.getContentType());
        return photoRepository.save(photo);
    }

    public Photo getPhotoMetadata(String name){
        return photoRepository.findByName(name);
    }

    public List<Photo> findByIds(List<Integer> ids){
        return photoRepository.findAllById(ids);
    }

    public void deletePhoto(Photo photo){
        photoRepository.delete(photo);
    }
    // Get all photo urls by real estate id
    public List<String> getUrlsByRealEstateId(Integer id){
        return photoRepository.findUrlsByRealEstateId(id);
    }
    // Get all photo urls by project id
    public List<String> getPhotoUrlsByProjectId(Integer id){
        return photoRepository.findPhotoUrlsByProjectId(id);
    }

    // Get all photo urls by master layout id
    public List<String> getPhotoUrlsByMasterLayoutId(Integer id){
        return photoRepository.findPhotoUrlsByMasterLayoutId(id);
    }

    // Get all photo urls by region link id
    public List<String> getPhotoUrlsByRegionLinkId(Integer id){
        return photoRepository.findPhotoUrlsByRegionLinkId(id);
    }

    // Get all photo urls by Utilities id
    public List<String> getPhotoUrlsByUtilitiesId(Integer id){
        return photoRepository.findPhotoUrlsByUtilitiesId(id);
    }

    // Get photo url by customer id
    public List<String> getPhotoUrlByCustomerId(Integer id){
        return photoRepository.findPhotoUrlByCustomerId(id);
    }

    // Get photo url by employee id
    public List<String> getPhotoUrlsByEmployeeId(Integer id){
        return photoRepository.findPhotoUrlsByEmployeeId(id);
    }

    public Photo save(Photo photo){
        return photoRepository.save(photo);
    }

}
