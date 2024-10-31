package devcamp.realestateexchange.exceptions;

// Exception for file download in Spring Boot
public class FileDownloadException extends SpringBootFileUploadException {
    public FileDownloadException(String message) {
        super(message);
    }
 }
