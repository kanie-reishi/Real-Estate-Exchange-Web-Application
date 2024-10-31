package devcamp.realestateexchange.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import devcamp.realestateexchange.dto.APIResponse;
import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.exceptions.FileEmptyException;
import devcamp.realestateexchange.exceptions.FileUploadException;
import devcamp.realestateexchange.services.interfacep.IFileService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/photo")
@Validated
public class FileUploadController {
    private final IFileService fileService;

    public FileUploadController(IFileService fileUploadService) {
        this.fileService = fileUploadService;
    }

    // Upload file API
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile)
            throws FileEmptyException, FileUploadException, IOException {
        if (multipartFile.isEmpty()) {
            throw new FileEmptyException("File is empty. Cannot save an empty file");
        }
        boolean isValidFile = isValidFile(multipartFile);
        List<String> allowedFileExtensions = new ArrayList<>(Arrays.asList("png", "jpg", "jpeg"));
        if (isValidFile
                && allowedFileExtensions.contains(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))) {
            Photo photo = fileService.uploadFile(multipartFile);
            APIResponse apiResponse = APIResponse.builder()
                    .message("file uploaded successfully")
                    .data(photo)
                    .isSuccessful(true)
                    .statusCode(200)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            APIResponse apiResponse = APIResponse.builder()
                    .message("Invalid File. File extension or File name is not supported")
                    .isSuccessful(false)
                    .statusCode(400)
                    .build();
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete file API
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("fileName") @NotBlank @NotNull String fileName) {
        boolean isDeleted = fileService.delete(fileName);
        if (isDeleted) {
            APIResponse apiResponse = APIResponse.builder().message("file deleted!")
                    .statusCode(200).build();
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        } else {
            APIResponse apiResponse = APIResponse.builder().message("file does not exist")
                    .statusCode(404).build();
            return new ResponseEntity<>("file does not exist", HttpStatus.NOT_FOUND);
        }
    }

    // Check if file is valid
    private boolean isValidFile(MultipartFile multipartFile) {
        log.info("Empty Status ==> {}", multipartFile.isEmpty());
        if (Objects.isNull(multipartFile.getOriginalFilename())) {
            return false;
        }
        return !multipartFile.getOriginalFilename().trim().equals("");
    }
}
