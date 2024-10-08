package devcamp.realestateexchange.services.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.exceptions.FileDownloadException;
import devcamp.realestateexchange.services.interfacep.IFileService;
import devcamp.realestateexchange.services.media.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements IFileService {
    @Value("${aws.bucket.name}")
    private String bucketName;
 
    private final AmazonS3 s3Client;
    @Autowired
    private PhotoService photoService;
    @Override
    public Photo uploadFile(MultipartFile multipartFile) throws IOException {
        // convert multipart file  to a file
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
            fileOutputStream.write(multipartFile.getBytes());
        }
 
        // generate file name
        String fileName = generateFileName(multipartFile);
 
        // upload file
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/"+ FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        metadata.addUserMetadata("Title", "File Upload - " + fileName);
        metadata.setContentLength(file.length());
        request.setMetadata(metadata);
        s3Client.putObject(request);
        
        // generate file url
        String url = s3Client.getUrl(bucketName, fileName).toExternalForm();

        // delete file
        file.delete();

        // save photo data
        return photoService.savePhotoMetadata(multipartFile, url);
    }
 
    @Override
    public Object downloadFile(String fileName) throws FileDownloadException, IOException {
        // check if bucket is empty
        if (bucketIsEmpty()) {
            throw new FileDownloadException("Requested bucket does not exist or is empty");
        }
        // download file
        S3Object object = s3Client.getObject(bucketName, fileName);
        // create a temp file
        try (S3ObjectInputStream s3is = object.getObjectContent()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                // create a buffer
                byte[] read_buf = new byte[1024];
                int read_len = 0;
                // write to file
                while ((read_len = s3is.read(read_buf)) > 0) {
                    fileOutputStream.write(read_buf, 0, read_len);
                }
            }
            // create a resources object
            Path pathObject = Paths.get(fileName);
            Resource resource = new UrlResource(pathObject.toUri());
            // check if file exists
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileDownloadException("Could not find the file!");
            }
        }
    }
 
    @Override
    public boolean delete(String fileName) {
        File file = Paths.get(fileName).toFile();
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }
 
    private boolean bucketIsEmpty() {
        // check if bucket is empty by listing objects
        ListObjectsV2Result result = s3Client.listObjectsV2(this.bucketName);
        if (result == null){
            return false;
        }
        // get objects
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        return objects.isEmpty();
    }
    // generate file name
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }
 }
