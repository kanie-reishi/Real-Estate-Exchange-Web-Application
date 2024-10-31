package devcamp.realestateexchange.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Tạo một Bean ModelMapper
// ModelMapper giúp chúng ta chuyển đổi dữ liệu từ một đối tượng sang đối tượng khác
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        return modelMapper;
    }
}
