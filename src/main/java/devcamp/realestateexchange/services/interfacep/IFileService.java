package devcamp.realestateexchange.services.interfacep;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.exceptions.FileDownloadException;
import devcamp.realestateexchange.exceptions.FileUploadException;

public interface IFileService {
    Photo uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException;
    
    Object downloadFile(String fileName) throws FileDownloadException, IOException;

    boolean delete(String fileName);

}
