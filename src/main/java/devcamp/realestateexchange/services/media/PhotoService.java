package devcamp.realestateexchange.services.media;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import devcamp.realestateexchange.entity.media.Photo;
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

    public List<String> getUrlsByRealEstateId(Integer id){
        return photoRepository.findUrlsByRealEstateId(id);
    }
}
