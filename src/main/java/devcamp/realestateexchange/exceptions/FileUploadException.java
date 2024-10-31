package devcamp.realestateexchange.exceptions;

// Exception for file upload in Spring Boot
public class FileUploadException extends SpringBootFileUploadException{

    public FileUploadException(String message) {
        super(message);
    }
 }
