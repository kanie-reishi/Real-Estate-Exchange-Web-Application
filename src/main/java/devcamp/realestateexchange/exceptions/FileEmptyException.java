package devcamp.realestateexchange.exceptions;

// Exception for file upload in Spring Boot
public class FileEmptyException extends SpringBootFileUploadException {
    public FileEmptyException(String message) {
        super(message);
    }
 }
