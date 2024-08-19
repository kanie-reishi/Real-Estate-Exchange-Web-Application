package devcamp.realestateexchange.services.realestate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import devcamp.realestateexchange.dto.location.AddressDto;
import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
import devcamp.realestateexchange.entity.location.District;
import devcamp.realestateexchange.entity.location.Province;
import devcamp.realestateexchange.entity.location.Street;
import devcamp.realestateexchange.entity.location.Ward;
import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.realestate.ApartDetail;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.event.RealEstateChangedEvent;
import devcamp.realestateexchange.event.RealEstateChangedEventHandler;
import devcamp.realestateexchange.models.RealEstateSearchParameters;
import devcamp.realestateexchange.projections.RealEstateBasicProjection;
import devcamp.realestateexchange.repositories.location.IDistrictRepository;
import devcamp.realestateexchange.repositories.location.IProvinceRepository;
import devcamp.realestateexchange.repositories.location.IStreetRepository;
import devcamp.realestateexchange.repositories.location.IWardRepository;
import devcamp.realestateexchange.repositories.realestate.IRealEstateRepository;
import devcamp.realestateexchange.repositories.user.ICustomerRepository;
import devcamp.realestateexchange.services.media.PhotoService;

@Service
public class RealEstateService {
    @Autowired
    private IRealEstateRepository realEstateRepository;
    @Autowired
    private IProvinceRepository provinceReposotory;
    @Autowired
    private IDistrictRepository districtRepository;
    @Autowired
    private IWardRepository wardRepository;
    @Autowired
    private IStreetRepository streetRepository;
    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    PhotoService photoService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private RealEstateChangedEventHandler realEstateChangedEventHandler;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RestClient client;
    private static final Logger logger = LoggerFactory.getLogger(RealEstateChangedEventHandler.class);

    public Page<RealEstateDto> getAllRealEstateDtos(String searchTerm, Pageable pageable) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            Page<RealEstateBasicProjection> projections = realEstateRepository.findAllBasicProjections(pageable);
            return projections.map(this::convertBasicProjectionToDto);
        }
        return null;
    }

    private RealEstateDto convertBasicProjectionToDto(RealEstateBasicProjection projection) {
        RealEstateDto dto = new RealEstateDto();
        dto.setId(projection.getId());
        dto.setTitle(projection.getTitle());
        dto.setType(projection.getType());
        dto.setRequest(projection.getRequest());
        dto.setRealEstateCode(projection.getRealEstateCode());
        dto.setPrice(projection.getPrice());
        dto.setPriceUnit(projection.getPriceUnit());
        dto.setAcreage(projection.getAcreage());
        dto.setAcreageUnit(projection.getAcreageUnit());
        dto.setBedroom(projection.getBedroom());
        dto.setVerify(projection.getVerify());
        dto.setCreatedAt(projection.getCreatedAt());

        CustomerDto customerDto = new CustomerDto();
        if (projection.getCustomer() != null) { // Check null
            customerDto.setId(projection.getCustomer().getId());
            customerDto.setFullName(projection.getCustomer().getFullName());
            customerDto.setPhone(projection.getCustomer().getPhone());
        }
        dto.setCustomer(customerDto);

        AddressDto addressDto = new AddressDto();
        ProvinceDto provinceDto = new ProvinceDto();
        DistrictDto districtDto = new DistrictDto();

        if (projection.getProvince() != null) { // Check null
            provinceDto.setId(projection.getProvince().getId());
            provinceDto.setName(projection.getProvince().getName());
            addressDto.setProvince(provinceDto);
        }
        addressDto.setProvince(provinceDto);

        if (projection.getDistrict() != null) { // Check null
            districtDto.setId(projection.getDistrict().getId());
            districtDto.setName(projection.getDistrict().getName());
            districtDto.setPrefix(projection.getDistrict().getPrefix());
        }
        addressDto.setDistrict(districtDto);

        dto.setAddress(addressDto);

        List<String> photoUrls = photoService.getUrlsByRealEstateId(dto.getId());
        dto.setPhotoUrls(photoUrls);
        return dto;
    }

    /*
     * public RealEstateDto getRealEstateDtoById(Integer id) {
     * RealEstateDto realEstateDto = realEstateRepository.findDtoById(id)
     * .orElseThrow(() -> new RuntimeException("RealEstate not found"));
     * List<String> photoUrls = photoService.getUrlsByRealEstateId(id);
     * realEstateDto.setPhotoUrls(photoUrls);
     * return realEstateDto;
     * }
     */
    public RealEstateDto saveRealEstate(RealEstateDto realEstateDto) {
        RealEstate realEstate = new RealEstate();
        realEstate.setTitle(realEstateDto.getTitle());
        realEstate.setType(realEstateDto.getType());
        realEstate.setDescription(realEstateDto.getDescription());
        realEstate.setRequest(realEstateDto.getRequest());
        realEstate.setPrice(realEstateDto.getPrice());
        realEstate.setPriceUnit(realEstateDto.getPriceUnit());
        realEstate.setAcreage(realEstateDto.getAcreage());
        realEstate.setAcreageUnit(realEstateDto.getAcreageUnit());
        realEstate.setBedroom(realEstateDto.getBedroom());
        realEstate.setCreatedAt(realEstateDto.getCreatedAt());
        realEstate.setDetail(realEstateDto.getDetail().toEntity());
        Customer customer = customerRepository.findById(realEstateDto.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        if (customer != null) {
            realEstate.setCustomer(customer);
        } else {
            throw new RuntimeException("Customer not found");
        }
        // Link sented photo of real estate
        List<Photo> photos = photoService.findByIds(realEstateDto.getPhotoIds());
        realEstate.setPhotos(photos);
        Province province = provinceReposotory.findById(realEstateDto.getAddress().getProvince().getId())
                .orElseThrow(() -> new RuntimeException("Province not found"));
        if (province != null) {
            realEstate.setProvince(province);
        } else {
            throw new RuntimeException("Province not found");
        }

        District district = districtRepository.findById(realEstateDto.getAddress().getDistrict().getId())
                .orElseThrow(() -> new RuntimeException("District not found"));
        if (district != null) {
            realEstate.setDistrict(district);
        } else {
            throw new RuntimeException("District not found");
        }

        Ward ward = wardRepository.findById(realEstateDto.getAddress().getWard().getId())
                .orElseThrow(() -> new RuntimeException("Ward not found"));
        if (ward != null) {
            realEstate.setWard(ward);
        } else {
            throw new RuntimeException("Ward not found");
        }

        Street street = streetRepository.findById(realEstateDto.getAddress().getStreet().getId())
                .orElseThrow(() -> new RuntimeException("Street not found"));
        if (street != null) {
            realEstate.setStreet(street);
        } else {
            throw new RuntimeException("Street not found");
        }

        if (realEstateDto.getType() == 2) {
            realEstate.setApartDetail(new ApartDetail(realEstateDto.getApartDetail().getApartCode(),
                    realEstateDto.getApartDetail().getApartLoca(), realEstateDto.getApartDetail().getApartType(),
                    realEstateDto.getApartDetail().getNumberFloors()));
        }
        realEstate = realEstateRepository.save(realEstate);
        eventPublisher.publishEvent(new RealEstateChangedEvent(realEstateDto));
        return new RealEstateDto(realEstate);
    }

    public void indexAllRealEstates() {
        List<RealEstate> realEstates = realEstateRepository.findAll();
        createIndexWithCustomAnalyzer();
        for (RealEstate realEstate : realEstates) {
            RealEstateDto realEstateDto = convertRealEstateToDtoSearch(realEstate);
            eventPublisher.publishEvent(new RealEstateChangedEvent(realEstateDto));
        }
    }

    public List<RealEstate> search(RealEstateSearchParameters realEstateSearchParameters) {
        List<RealEstate> result = new ArrayList<>();

        try {
            Request request = new Request("POST", "/realestate_index/_search");
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode rootNode = mapper.createObjectNode();
            if (realEstateSearchParameters.getSize() != null) {
                rootNode.put("size", realEstateSearchParameters.getSize());
            }
            ObjectNode queryNode = rootNode.putObject("query");
            ObjectNode boolNode = queryNode.putObject("bool");
            ObjectNode mustNode = boolNode.putObject("must");

            if (realEstateSearchParameters.getSearchText() != null) {
                // Add the multi_match query to the bool query
                ObjectNode multiMatchNode = boolNode.putObject("multi_match");
                String text = realEstateSearchParameters.getSearchText();
                multiMatchNode.put("query", text);
                multiMatchNode.put("analyzer", "vietnamese_analyzer");
                // Specify the fields to search
                ArrayNode fieldsNode = multiMatchNode.putArray("fields");
                fieldsNode.add("title");
                fieldsNode.add("description");
                // Add the fuzzy query to the bool query
                ArrayNode shouldNode = boolNode.putArray("should");
                ObjectNode fuzzyNode = shouldNode.addObject().putObject("fuzzy");
                fuzzyNode.putObject("address").put("value", text);
            }

            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            logger.info("jsonString {}", jsonString);
            request.setJsonEntity(jsonString);

            // Add API key to request header
            Response response = client.performRequest(request);
            // Parse the response
            String responseBody = EntityUtils.toString(response.getEntity());
            logger.info("response body {}", responseBody);
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray hits = jsonObject.getJSONObject("hits").getJSONArray("hits");
            // Convert each hit to a RealEstateDto and add it to the result list
            for (int i = 0; i < hits.length(); i++) {
                JSONObject hit = hits.getJSONObject(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void createIndexWithCustomAnalyzer() {
        try {
            Request request = new Request("PUT", "/realestate_index");
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode rootNode = mapper.createObjectNode();

            ObjectNode settingsNode = rootNode.putObject("settings");
            ObjectNode analysisNode = settingsNode.putObject("analysis");
            ObjectNode analyzerNode = analysisNode.putObject("analyzer");
            ObjectNode vietnameseAnalyzerNode = analyzerNode.putObject("vietnamese_analyzer");
            vietnameseAnalyzerNode.put("tokenizer", "standard");
            ArrayNode filterNode = vietnameseAnalyzerNode.putArray("filter");
            // Add the vietnamese analyzer filters
            filterNode.add("lowercase");
            filterNode.add("asciifolding");

            ObjectNode mappingsNode = rootNode.putObject("mappings");
            ObjectNode propertiesNode = mappingsNode.putObject("properties");
            // Add the title field to the properties
            ObjectNode titleNode = propertiesNode.putObject("title");
            titleNode.put("type", "text");
            titleNode.put("analyzer", "vietnamese_analyzer");
            // Add the address field to the properties
            ObjectNode addressNode = propertiesNode.putObject("address");
            addressNode.put("type", "text");
            addressNode.put("analyzer", "vietnamese_analyzer");
            // Add the description field to properties
            ObjectNode descriptionNode = propertiesNode.putObject("description");
            descriptionNode.put("type", "text");
            descriptionNode.put("analyzer", "vietnamese_analyzer");
            // Convert the JSON object to a string
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            // Set the JSON entity of the request
            request.setJsonEntity(jsonString);
            // Send the request
            Response response = client.performRequest(request);
            // Handle the response...
            logger.info("Response {}", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public RealEstateDto convertRealEstateToDtoSearch(RealEstate realEstate){
        RealEstateDto realEstateDto = new RealEstateDto();
        realEstateDto.setType(realEstate.getType());
        realEstateDto.setRequest(realEstate.getRequest());
        realEstateDto.setRealEstateCode(realEstate.getRealEstateCode());
        realEstateDto.setPrice(realEstate.getPrice());
        realEstateDto.setPriceUnit(realEstate.getPriceUnit());
        realEstateDto.setAcreage(realEstate.getAcreage());
        realEstateDto.setAcreageUnit(realEstate.getAcreageUnit());
        realEstateDto.setBedroom(realEstate.getBedroom());
        realEstateDto.setBath(realEstate.getBath());
        realEstateDto.setTotalFloors(realEstate.getTotalFloors());
        realEstateDto.setDirection(realEstate.getDirection());
        realEstateDto.setVerify(realEstate.getVerify());
        realEstateDto.setTitle(realEstate.getTitle());
        realEstateDto.setDescription(realEstate.getDescription());

        return realEstateDto;
    }
}
