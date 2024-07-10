package devcamp.realestateexchange.services.interfacep;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import devcamp.realestateexchange.exceptions.FileDownloadException;
import devcamp.realestateexchange.exceptions.FileUploadException;

public interface IFileService {
    String uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException;

    String uploadFile(MultipartFile multipartFile, int realEstateId) throws IOException;
    
    Object downloadFile(String fileName) throws FileDownloadException, IOException;

    boolean delete(String fileName);

}
