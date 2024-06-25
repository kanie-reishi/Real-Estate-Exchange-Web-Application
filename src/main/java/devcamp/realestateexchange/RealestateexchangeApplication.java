package devcamp.realestateexchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import org.modelmapper.ModelMapper;
@SpringBootApplication
public class RealestateexchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealestateexchangeApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		return modelMapper;
	}
}
