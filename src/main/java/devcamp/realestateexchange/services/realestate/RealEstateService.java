package devcamp.realestateexchange.services.realestate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import devcamp.realestateexchange.dto.location.AddressDto;
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

    private static final SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public Page<RealEstateDto> getAllRealEstateDtos(Pageable pageable) {
        Page<RealEstateBasicProjection> projections = realEstateRepository.findAllBasicProjections(pageable);
        return projections.map(this::convertBasicProjectionToDto);
    }

    public RealEstate getRealEstateById(Integer id) {
        return realEstateRepository.findById(id).orElse(null);
    }

    public RealEstate deleteRealEstateById(Integer id) {
        RealEstate realEstate = realEstateRepository.findById(id).orElse(null);
        if (realEstate != null) {
            realEstateRepository.delete(realEstate);
        }
        // Delete document in Elasticsearch
        Request request = new Request("DELETE", "/realestate_index/_doc/" + id);
        try {
            client.performRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return realEstate;
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
        if (projection.getCreatedAt() != null) {
            String createdAtIso = isoFormat.format(projection.getCreatedAt());
            dto.setCreatedAt(createdAtIso);
        } else {
            dto.setCreatedAt(null);
        }
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

        dto.setAddressDetail(addressDto);

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

        Date createdAt = null;

        try {
            createdAt = isoFormat.parse(realEstateDto.getCreatedAt());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid date format for createdAt");
        }

        realEstate.setCreatedAt(createdAt);
        realEstate.setDetail(realEstateDto.getDetail().toEntity());
        realEstate.setAddress(realEstateDto.getAddress());
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
        Province province = provinceReposotory.findById(realEstateDto.getAddressDetail().getProvince().getId())
                .orElseThrow(() -> new RuntimeException("Province not found"));
        if (province != null) {
            realEstate.setProvince(province);
        } else {
            throw new RuntimeException("Province not found");
        }

        District district = districtRepository.findById(realEstateDto.getAddressDetail().getDistrict().getId())
                .orElseThrow(() -> new RuntimeException("District not found"));
        if (district != null) {
            realEstate.setDistrict(district);
        } else {
            throw new RuntimeException("District not found");
        }

        Ward ward = wardRepository.findById(realEstateDto.getAddressDetail().getWard().getId())
                .orElseThrow(() -> new RuntimeException("Ward not found"));
        if (ward != null) {
            realEstate.setWard(ward);
        } else {
            throw new RuntimeException("Ward not found");
        }

        Street street = streetRepository.findById(realEstateDto.getAddressDetail().getStreet().getId())
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

    public void indexTestRealEstate() {
        RealEstate realEstate = realEstateRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("RealEstate not found"));
        createIndexWithCustomAnalyzer();
        RealEstateDto realEstateDto = convertRealEstateToDtoSearch(realEstate);
        eventPublisher.publishEvent(new RealEstateChangedEvent(realEstateDto));
    }

    public Page<RealEstateDto> search(RealEstateSearchParameters realEstateSearchParameters) {
        List<RealEstateDto> result = new ArrayList<>();

        try {
            Request request = new Request("POST", "/realestate_index/_search");
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode rootNode = mapper.createObjectNode();
            if (realEstateSearchParameters.getSize() != null) {
                rootNode.put("size", realEstateSearchParameters.getSize());
            }
            if (realEstateSearchParameters.getFrom() != null) {
                rootNode.put("from", realEstateSearchParameters.getFrom());
            }
            // Add the query to the root node
            ObjectNode queryNode = rootNode.putObject("query");
            ObjectNode boolNode = queryNode.putObject("bool");
            ArrayNode mustNode = boolNode.putArray("must");
            // Add the multi_match query to the bool query
            if (realEstateSearchParameters.getSearchText() != null
                    && !realEstateSearchParameters.getSearchText().isEmpty()) {
                // Add the multi_match query to the bool query
                ObjectNode multiMatchNode = mustNode.addObject().putObject("multi_match");
                String text = realEstateSearchParameters.getSearchText();
                multiMatchNode.put("query", text);
                multiMatchNode.put("analyzer", "vietnamese_analyzer");
                multiMatchNode.putArray("fields").add("title").add("description").add("address");
                multiMatchNode.put("type", "best_fields");
                multiMatchNode.put("fuzziness", "AUTO"); // Apply fuzzy search
                // You can set this to a specific value like "1", "2", etc.
            }
            // Add the filters to the bool query
            if (realEstateSearchParameters.getType() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("type", realEstateSearchParameters.getType());
            }
            if (realEstateSearchParameters.getRequest() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("request", realEstateSearchParameters.getRequest());
            }
            if (realEstateSearchParameters.getRealEstateCode() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("realEstateCode", realEstateSearchParameters.getRealEstateCode());
            }
            if (realEstateSearchParameters.getPrice() != null && realEstateSearchParameters.getPriceUnit() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("price", realEstateSearchParameters.getPrice());
                matchNode.put("priceUnit", realEstateSearchParameters.getPriceUnit());
            }
            if (realEstateSearchParameters.getAcreage() != null
                    && realEstateSearchParameters.getAcreageUnit() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("acreage", realEstateSearchParameters.getAcreage());
                matchNode.put("acreageUnit", realEstateSearchParameters.getAcreageUnit());
            }
            if (realEstateSearchParameters.getMinPrice() != null) {
                ObjectNode rangeNode = mustNode.addObject().putObject("range");
                rangeNode.putObject("price").put("gte", realEstateSearchParameters.getMinPrice());
            }
            if (realEstateSearchParameters.getMaxPrice() != null) {
                ObjectNode rangeNode = mustNode.addObject().putObject("range");
                rangeNode.putObject("price").put("lte", realEstateSearchParameters.getMaxPrice());
            }
            if (realEstateSearchParameters.getMinAcreage() != null) {
                ObjectNode rangeNode = mustNode.addObject().putObject("range");
                rangeNode.putObject("acreage").put("gte", realEstateSearchParameters.getMinAcreage());
            }
            if (realEstateSearchParameters.getMaxAcreage() != null) {
                ObjectNode rangeNode = mustNode.addObject().putObject("range");
                rangeNode.putObject("acreage").put("lte", realEstateSearchParameters.getMaxAcreage());
            }
            if (realEstateSearchParameters.getBedroom() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("bedroom", realEstateSearchParameters.getBedroom());
            }
            if (realEstateSearchParameters.getBathroom() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("bath", realEstateSearchParameters.getBathroom());
            }
            if (realEstateSearchParameters.getFloor() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("totalFloors", realEstateSearchParameters.getFloor());
            }
            if (realEstateSearchParameters.getDirection() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("direction", realEstateSearchParameters.getDirection());
            }
            if (realEstateSearchParameters.getVerify() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("verify", realEstateSearchParameters.getVerify());
            }

            if (realEstateSearchParameters.getProvinceId() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("address.province.id", realEstateSearchParameters.getProvinceId());
            }

            if (realEstateSearchParameters.getDistrictId() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("address.district.id", realEstateSearchParameters.getDistrictId());
            }

            if (realEstateSearchParameters.getWardId() != null) {
                ObjectNode matchNode = mustNode.addObject().putObject("match");
                matchNode.put("address.ward.id", realEstateSearchParameters.getWardId());
            }
            if (realEstateSearchParameters.getCreatedAt() != null) {
                ObjectNode rangeNode = mustNode.addObject().putObject("range");
                ObjectNode createdAtNode = rangeNode.putObject("createdAt");
                String createdAtIso = isoFormat.format(realEstateSearchParameters.getCreatedAt());
                createdAtNode.put("gte", createdAtIso);
            }
            // Add sort to the root node
            if (realEstateSearchParameters.getSort() != null) {
                ObjectNode sortNode = rootNode.putObject("sort");
                ObjectNode sortFieldNode = sortNode.putObject(realEstateSearchParameters.getSort());
                sortFieldNode.put("order", realEstateSearchParameters.getOrder());
            }
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            logger.info("Request {}", jsonString);
            request.setJsonEntity(jsonString);

            // Add API key to request header
            Response response = client.performRequest(request);
            // Parse the response
            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseBody);
            logger.info("responseBody {}", responseBody);
            JSONArray hits = jsonObject.getJSONObject("hits").getJSONArray("hits");
            // Get the total number of hits
            long totalHits = jsonObject.getJSONObject("hits").getJSONObject("total").getInt("value");
            // Convert each hit to a RealEstateDto and add it to the result list
            for (int i = 0; i < hits.length(); i++) {
                JSONObject hit = hits.getJSONObject(i);
                JSONObject source = hit.getJSONObject("_source");
                RealEstateDto realEstateDto = mapper.readValue(source.toString(), RealEstateDto.class);
                addPhotoUrls(realEstateDto);
                result.add(realEstateDto);
            }
            int start = realEstateSearchParameters.getFrom() != null ? realEstateSearchParameters.getFrom() : 0;
            int size = realEstateSearchParameters.getSize() != null ? realEstateSearchParameters.getSize()
                    : result.size();
            if (size == 0) {
                return new PageImpl<>(result);
            }
            Pageable pageable = PageRequest.of(start / size, size);
            int end = Math.min((start + size), result.size());

            Page<RealEstateDto> page = new PageImpl<>(result, pageable, totalHits);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageImpl<>(result);
        }
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
            // Add the description field to properties
            ObjectNode descriptionNode = propertiesNode.putObject("description");
            descriptionNode.put("type", "text");
            descriptionNode.put("analyzer", "vietnamese_analyzer");
            // Add the address field to properties
            ObjectNode addressNode = propertiesNode.putObject("address");
            addressNode.put("type", "text");
            addressNode.put("analyzer", "vietnamese_analyzer");
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

    public RealEstateDto convertRealEstateToDtoSearch(RealEstate realEstate) {
        RealEstateDto realEstateDto = new RealEstateDto();
        realEstateDto.setId(realEstate.getId());
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
        realEstateDto.setAddress(realEstate.getAddress());

        AddressDto addressDto = new AddressDto();
        ProvinceDto provinceDto = new ProvinceDto();
        DistrictDto districtDto = new DistrictDto();
        WardDto wardDto = new WardDto();
        StreetDto streetDto = new StreetDto();
        if (realEstate.getProvince() != null) {
            provinceDto.setId(realEstate.getProvince().getId());
            provinceDto.setName(realEstate.getProvince().getName());
            addressDto.setProvince(provinceDto);
        }
        if (realEstate.getDistrict() != null) {
            districtDto.setId(realEstate.getDistrict().getId());
            districtDto.setName(realEstate.getDistrict().getName());
            districtDto.setPrefix(realEstate.getDistrict().getPrefix());
            addressDto.setDistrict(districtDto);
        }
        if (realEstate.getWard() != null) {
            wardDto.setId(realEstate.getWard().getId());
            wardDto.setName(realEstate.getWard().getName());
            addressDto.setWard(wardDto);
        }
        if (realEstate.getStreet() != null) {
            streetDto.setId(realEstate.getStreet().getId());
            streetDto.setName(realEstate.getStreet().getName());
            addressDto.setStreet(streetDto);
        }
        addressDto.setProvince(provinceDto);
        addressDto.setDistrict(districtDto);
        addressDto.setWard(wardDto);
        addressDto.setStreet(streetDto);
        realEstateDto.setAddressDetail(addressDto);

        CustomerDto customerDto = new CustomerDto();
        if (realEstate.getCustomer() != null) {
            customerDto.setId(realEstate.getCustomer().getId());
            customerDto.setFullName(realEstate.getCustomer().getFullName());
            customerDto.setPhone(realEstate.getCustomer().getPhone());
        }
        realEstateDto.setCustomer(customerDto);
        // Chuyển đổi createdAt thành định dạng ISO 8601
        if (realEstate.getCreatedAt() != null) {
            String createdAtIso = isoFormat.format(realEstate.getCreatedAt());
            realEstateDto.setCreatedAt(createdAtIso);
        } else {
            // Xử lý trường hợp thiếu trường createdAt
            realEstateDto.setCreatedAt("N/A"); // Hoặc giá trị mặc định khác
        }
        return realEstateDto;
    }

    public void addPhotoUrls(RealEstateDto realEstateDto) {
        List<String> photoUrls = photoService.getUrlsByRealEstateId(realEstateDto.getId());
        realEstateDto.setPhotoUrls(photoUrls);
    }
}
