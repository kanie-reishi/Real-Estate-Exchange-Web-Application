package devcamp.realestateexchange.config;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3ClientConfig {
   // AWS Access Key
   @Value("${aws.accessKey}")
   private String accessKey;
   // AWS Secret Key
   @Value("${aws.secretKey}")
   private String secretKey;

   @Bean
   public AmazonS3 initS3Client(){
      // Tạo một Bean AmazonS3
      // Sử dụng AWS Access Key và AWS Secret Key để xác thực
       AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
       return AmazonS3ClientBuilder.standard()
               .withRegion(Regions.AP_SOUTHEAST_1)
               .withCredentials(new AWSStaticCredentialsProvider(credentials))
               .build();
   }

}
