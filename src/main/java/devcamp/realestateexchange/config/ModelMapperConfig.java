package devcamp.realestateexchange.config;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.dto.location.StreetDto;
import devcamp.realestateexchange.dto.location.WardDto;
import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
import devcamp.realestateexchange.entity.location.District;
import devcamp.realestateexchange.entity.location.Province;
import devcamp.realestateexchange.entity.location.Street;
import devcamp.realestateexchange.entity.location.Ward;
import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.entity.user.Customer;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.getConfiguration().setFullTypeMatchingRequired(true);
        // Define mapping for Province
        modelMapper.createTypeMap(Province.class, ProvinceDto.class)
                .addMappings(mapper -> {
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((Province) ctx.getSource()).getId())
                            .map(Province::getId, ProvinceDto::setId);
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((Province) ctx.getSource()).getName())
                            .map(Province::getName, ProvinceDto::setName);
                });
        // Define mapping for District
        modelMapper.createTypeMap(District.class, DistrictDto.class)
                .addMappings(mapper -> {
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((District) ctx.getSource()).getId())
                            .map(District::getId, DistrictDto::setId);
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((District) ctx.getSource()).getName())
                            .map(District::getName, DistrictDto::setName);
                });
        // Define mapping for Ward
        modelMapper.createTypeMap(Ward.class, WardDto.class)
                .addMappings(mapper -> {
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((Ward) ctx.getSource()).getId())
                            .map(Ward::getId, WardDto::setId);
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((Ward) ctx.getSource()).getName())
                            .map(Ward::getName, WardDto::setName);
                });
        // Define mapping for Street
        modelMapper.createTypeMap(Street.class, StreetDto.class)
                .addMappings(mapper -> {
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((Street) ctx.getSource()).getId())
                            .map(Street::getId, StreetDto::setId);
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((Street) ctx.getSource()).getName())
                            .map(Street::getName, StreetDto::setName);
                });
        // Define mapping for Customer
        modelMapper.createTypeMap(Customer.class, CustomerDto.class)
                .addMappings(mapper -> {
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((Customer) ctx.getSource()).getId())
                            .map(Customer::getId, CustomerDto::setId);
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((Customer) ctx.getSource()).getContactName())
                            .map(Customer::getContactName, CustomerDto::setContactName);
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((Customer) ctx.getSource()).getContactTitle())
                            .map(Customer::getContactTitle, CustomerDto::setContactTitle);
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((Customer) ctx.getSource()).getPhone())
                            .map(Customer::getPhone, CustomerDto::setPhone);
                    mapper.using(ctx -> ctx.getSource() == null ? null : ((Customer) ctx.getSource()).getEmail())
                            .map(Customer::getEmail, CustomerDto::setEmail);
                });
        // Define mapping for RealEstate
        modelMapper.createTypeMap(RealEstate.class, RealEstateDto.class)
                .addMappings(mapper -> {
                    mapper.using(ctx -> {
                        RealEstate source = (RealEstate) ctx.getSource();
                        if (source == null || source.getPhotos() == null) {
                            return null;
                        }
                        return source.getPhotos().stream()
                                .map(Photo::getUrl)
                                .collect(Collectors.toList());
                    }).map(RealEstate::getPhotos, RealEstateDto::setPhotoUrls);
                    mapper.using(ctx -> {
                        RealEstate source = (RealEstate) ctx.getSource();
                        if (source == null || source.getCustomer() == null) {
                            return null;
                        }
                        return modelMapper.map(source.getCustomer(), CustomerDto.class);
                    }).map(RealEstate::getCustomer, RealEstateDto::setCustomer);
                });

        // Add more mappings here...

        return modelMapper;
    }
}
