package devcamp.realestateexchange.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import devcamp.realestateexchange.exceptions.FileDownloadException;
import devcamp.realestateexchange.exceptions.FileUploadException;

public interface FileService {
    String uploadFile(MultipartFile multipartFile) throws FileUploadException, IOException;

    Object downloadFile(String fileName) throws FileDownloadException, IOException;

    boolean delete(String fileName);
}