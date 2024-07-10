package devcamp.realestateexchange.services.media;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.repositories.media.IPhotoRepository;
import devcamp.realestateexchange.repositories.realestate.IRealEstateRepository;

import org.springframework.web.multipart.MultipartFile;
import devcamp.realestateexchange.exceptions.ResourceNotFoundException;
@Service
public class PhotoService {
    @Autowired
    private IPhotoRepository photoRepository;
    @Autowired
    private IRealEstateRepository realEstateRepository;
    public Photo savePhotoMetadata(MultipartFile file, String url,int realEstateId){
        RealEstate realEstate = realEstateRepository.findById(realEstateId)
            .orElseThrow(() -> new ResourceNotFoundException("RealEstate not found"));
        Photo photo = new Photo();
        photo.setName(file.getOriginalFilename());
        photo.setUrl(url);
        photo.setSize(file.getSize());
        photo.setType(file.getContentType());
        photo.setRealEstate(realEstate);
        return photoRepository.save(photo);
    }
    public Photo getPhotoMetadata(String name){
        return photoRepository.findByName(name);
    }
}
