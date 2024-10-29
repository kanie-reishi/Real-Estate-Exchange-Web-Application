package devcamp.realestateexchange.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
@Configuration
public class RedisConfig {
    // Redis Host ("example: redis-12345.c1.ap-southeast-1-1.ec2.cloud.redislabs.com")
    @Value("${spring.redis.host}")
    private String redisHost;
    // Redis Port
    @Value("${spring.redis.port}")
    private int redisPort;
    // Redis Password
    @Value("${spring.redis.password}")
    private String redisPassword;


    // Tạo một Bean LettuceConnectionFactory
   @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        redisConfig.setPassword(RedisPassword.of(redisPassword));
        // Tạo Standalone Connection tới Redis
        return new LettuceConnectionFactory(redisConfig);
    }

    // Tạo một Bean RedisTemplate
    @Bean
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // tạo ra một RedisTemplate
        // Với Key là Object
        // Value là Object
        // RedisTemplate giúp chúng ta thao tác với Redis
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
