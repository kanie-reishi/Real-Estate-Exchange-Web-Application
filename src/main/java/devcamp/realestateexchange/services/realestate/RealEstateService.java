package devcamp.realestateexchange.services.realestate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import devcamp.realestateexchange.dto.location.AddressDto;
import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.dto.location.StreetDto;
import devcamp.realestateexchange.dto.location.WardDto;
import devcamp.realestateexchange.dto.realestate.ProjectDto;
import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.dto.realestate.RealEstateDto.ApartDetailDto;
import devcamp.realestateexchange.dto.realestate.RealEstateDto.RealEstateDetailDto;
import devcamp.realestateexchange.dto.social.ArticleDto;
import devcamp.realestateexchange.dto.user.CustomerDto;
import devcamp.realestateexchange.entity.location.District;
import devcamp.realestateexchange.entity.location.Province;
import devcamp.realestateexchange.entity.location.Street;
import devcamp.realestateexchange.entity.location.Ward;
import devcamp.realestateexchange.entity.media.Photo;
import devcamp.realestateexchange.entity.realestate.ApartDetail;
import devcamp.realestateexchange.entity.realestate.Project;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.entity.realestate.RealEstateDetail;
import devcamp.realestateexchange.entity.user.Customer;
import devcamp.realestateexchange.event.RealEstateChangedEvent;
import devcamp.realestateexchange.event.RealEstateChangedEventHandler;
import devcamp.realestateexchange.models.RealEstateSearchParameters;
import devcamp.realestateexchange.projections.RealEstateProjection;
import devcamp.realestateexchange.projections.RealEstateExtendProjection;
import devcamp.realestateexchange.repositories.location.IDistrictRepository;
import devcamp.realestateexchange.repositories.location.IProvinceRepository;
import devcamp.realestateexchange.repositories.location.IStreetRepository;
import devcamp.realestateexchange.repositories.location.IWardRepository;
import devcamp.realestateexchange.repositories.realestate.IRealEstateRepository;
import devcamp.realestateexchange.repositories.user.ICustomerRepository;
import devcamp.realestateexchange.services.media.ArticleService;
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
    private PhotoService photoService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private RestClient client;
    private static final Logger logger = LoggerFactory.getLogger(RealEstateChangedEventHandler.class);

    private static final SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    // Phương thức lấy tất cả RealEstateDto
    @Transactional(readOnly = true)
    public Page<RealEstateDto> getAllRealEstateDtos(Pageable pageable, Integer verify) {
        if (verify == 0) {
            Page<RealEstateProjection> projections = realEstateRepository.findAllBasicProjections(pageable);
            if (projections == null) {
                return new PageImpl<>(Collections.emptyList());
            }
            return projections.map(this::convertProjectionToDto);
        }
        Page<RealEstateProjection> projections = realEstateRepository.findVerifiedRealEstates(pageable);
        if (projections == null) {
            return new PageImpl<>(Collections.emptyList());
        }
        return projections.map(this::convertProjectionToDto);
    }

    // Phương thức lấy RealEstateDto theo id
    public RealEstateDto getRealEstateById(Integer id) {
        RealEstateExtendProjection projection = realEstateRepository.findRealEstateById(id);
        if (projection != null) {
            return convertExtendProjectionToDto(projection);
        }
        return null;
    }

    // Phương thức xóa cứng RealEstateDto theo id
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
    // Phương thức xóa mềm RealEstateDto theo id
    public RealEstate softDeleteRealEstateById(Integer id) {
        RealEstate realEstate = realEstateRepository.findById(id).orElse(null);
        if (realEstate != null) {
            realEstate.setDeleted(true);
            realEstateRepository.save(realEstate);
        }
        return realEstate;
    }
    // Phương thức xác thực RealEstateDto theo id
    public RealEstate verifyRealEstateById(Integer id) {
        RealEstate realEstate = realEstateRepository.findById(id).orElse(null);
        if (realEstate != null) {
            realEstate.setVerify(1);
            realEstateRepository.save(realEstate);
        }
        return realEstate;
    }
    // Phương thức khôi phục RealEstateDto theo id
    public RealEstate restoreRealEstateById(Integer id) {
        RealEstate realEstate = realEstateRepository.findById(id).orElse(null);
        if (realEstate != null) {
            realEstate.setDeleted(false);
            realEstateRepository.save(realEstate);
        }
        return realEstate;
    }
    // Phương thức chuyển đổi RealEstateProjection thành RealEstateDto
    public RealEstateDto convertProjectionToDto(RealEstateProjection projection) {
        // Chuyển đổi RealEstateProjection thành RealEstateDto
        RealEstateDto dto = new RealEstateDto();
        dto.setId(projection.getId());
        dto.setTitle(projection.getTitle());
        dto.setType(projection.getType());
        dto.setRequest(projection.getRequest());
        dto.setRealEstateCode(projection.getRealEstateCode());
        dto.setPrice(projection.getPrice());
        dto.setPriceUnit(projection.getPriceUnit());
        dto.setPriceTime(projection.getPriceTime());
        dto.setAcreage(projection.getAcreage());
        dto.setAcreageUnit(projection.getAcreageUnit());
        dto.setBedroom(projection.getBedroom());
        dto.setVerify(projection.getVerify());
        dto.setDirection(projection.getDirection());
        dto.setTotalFloors(projection.getTotalFloors());
        dto.setBath(projection.getBath());
        dto.setDescription(projection.getDescription());
        // Chuyển đổi createdAt thành định dạng ISO 8601
        if (projection.getCreatedAt() != null) {
            String createdAtIso = isoFormat.format(projection.getCreatedAt());
            dto.setCreatedAt(createdAtIso);
        } else {
            dto.setCreatedAt(null);
        }
        // Thêm thông tin khách hàng vào RealEstateDto
        CustomerDto customerDto = new CustomerDto();
        if (projection.getCustomer() != null) { // Check null
            customerDto.setId(projection.getCustomer().getId());
            customerDto.setFullName(projection.getCustomer().getFullName());
            customerDto.setPhone(projection.getCustomer().getPhone());
        }
        dto.setCustomer(customerDto);
        // Thêm thông tin dự án vào RealEstateDto
        ProjectDto projectDto = new ProjectDto();

        dto.setProject(projectDto);
        // Thêm thông tin địa chỉ vào RealEstateDto
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

        if (projection.getWard() != null) { // Check null
            WardDto wardDto = new WardDto();
            wardDto.setId(projection.getWard().getId());
            wardDto.setName(projection.getWard().getName());
            wardDto.setPrefix(projection.getWard().getPrefix());
            addressDto.setWard(wardDto);
        }

        if (projection.getStreet() != null) { // Check null
            StreetDto streetDto = new StreetDto();
            streetDto.setId(projection.getStreet().getId());
            streetDto.setName(projection.getStreet().getName());
            streetDto.setPrefix(projection.getStreet().getPrefix());
            addressDto.setStreet(streetDto);
        }

        addressDto.setAddressDetail(projection.getAddress());

        dto.setAddressDetail(addressDto);
        // Thêm thông tin ảnh vào RealEstateDto
        List<String> photoUrls = photoService.getUrlsByRealEstateId(dto.getId());
        dto.setPhotoUrls(photoUrls);
        // Trả về RealEstateDto
        return dto;
    }

    // Phương thức chuyển đổi RealEstateExtendProjection thành RealEstateDto
    public RealEstateDto convertExtendProjectionToDto(RealEstateExtendProjection projection) {
        RealEstateDto dto = convertProjectionToDto(projection);
        ArticleDto articleDto = new ArticleDto();
        if (projection.getArticle() != null) {
            articleDto.setViewNum(projection.getArticle().getViewNum());
            articleDto.setLikeNum(projection.getArticle().getLikeNum());
            articleDto.setReplies(projection.getArticle().getReplies().stream()
                    .map(articleService::convertReplyProjectionToDto).collect(
                            Collectors.toSet()));
        }
        dto.setArticle(articleDto);
        RealEstateDetailDto detailDto = new RealEstateDetailDto();
        if (projection.getDetail() != null) {
            detailDto.setBalcony(projection.getDetail().getBalcony());
            detailDto.setPriceMin(projection.getDetail().getPriceMin());
            detailDto.setWallArea(projection.getDetail().getWallArea());
            detailDto.setLandscapeView(projection.getDetail().getLandscapeView());
            detailDto.setFurnitureType(projection.getDetail().getFurnitureType());
            detailDto.setFurnitureStatus(projection.getDetail().getFurnitureStatus());
            detailDto.setPriceRent(projection.getDetail().getPriceRent());
            detailDto.setReturnRate(projection.getDetail().getReturnRate());
            detailDto.setLegalDoc(projection.getDetail().getLegalDoc());
            detailDto.setWidthY(projection.getDetail().getWidthY());
            detailDto.setLongX(projection.getDetail().getLongX());
            detailDto.setStreetHouse(projection.getDetail().getStreetHouse());
            detailDto.setFSBO(projection.getDetail().getFSBO());
            detailDto.setShape(projection.getDetail().getShape());
            detailDto.setDistance2Facade(projection.getDetail().getDistance2Facade());
            detailDto.setAdjacentFacadeNum(projection.getDetail().getAdjacentFacadeNum());
            detailDto.setAdjacentRoad(projection.getDetail().getAdjacentRoad());
            detailDto.setAlleyMinWidth(projection.getDetail().getAlleyMinWidth());
            detailDto.setAdjacentAlleyMinWidth(projection.getDetail().getAdjacentAlleyMinWidth());
            detailDto.setStructure(projection.getDetail().getStructure());
            detailDto.setDTSXD(projection.getDetail().getDTSXD());
            detailDto.setCtxdPrice(projection.getDetail().getCtxdPrice());
            detailDto.setCtxdValue(projection.getDetail().getCtxdValue());
        }
        dto.setDetail(detailDto);

        ApartDetailDto apartDetailDto = new ApartDetailDto();
        if (projection.getApartDetail() != null) {
            apartDetailDto.setApartCode(projection.getApartDetail().getApartCode());
            apartDetailDto.setApartLoca(projection.getApartDetail().getApartLoca());
            apartDetailDto.setApartType(projection.getApartDetail().getApartType());
            apartDetailDto.setNumberFloors(projection.getApartDetail().getNumberFloors());
        }
        dto.setApartDetail(apartDetailDto);
        return dto;
    }

    // Phương thức lưu RealEstate
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
        // Parse createdAt from ISO 8601 format
        try {
            if (realEstateDto.getCreatedAt() != null) {
                createdAt = isoFormat.parse(realEstateDto.getCreatedAt());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid date format for createdAt");
        }

        realEstate.setCreatedAt(createdAt);
        if (realEstateDto.getDetail() != null) {
            realEstate.setDetail(realEstateDto.getDetail().toEntity());
        }
        realEstate.setAddress(realEstateDto.getAddress());
        if (realEstateDto.getCustomer() != null) {
            Customer customer = customerRepository.findById(realEstateDto.getCustomer().getId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            if (customer != null) {
                realEstate.setCustomer(customer);
            } else {
                throw new RuntimeException("Customer not found");
            }
        }
        // Link sented photo of real estate
        if (realEstateDto.getPhotoIds() != null) {
            List<Photo> photos = photoService.findByIds(realEstateDto.getPhotoIds());
            realEstate.setPhotos(photos);
        }
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
        if (realEstateDto.getType() != null) {
            if (realEstateDto.getType() == 2) {
                realEstate.setApartDetail(new ApartDetail(realEstateDto.getApartDetail().getApartCode(),
                        realEstateDto.getApartDetail().getApartLoca(), realEstateDto.getApartDetail().getApartType(),
                        realEstateDto.getApartDetail().getNumberFloors()));
            }
        }
        realEstate = realEstateRepository.save(realEstate);
        eventPublisher.publishEvent(new RealEstateChangedEvent(realEstateDto));
        return new RealEstateDto(realEstate);
    }

    // Phương thức index tất cả RealEstate
    public void indexAllRealEstates() {
        List<RealEstate> realEstates = realEstateRepository.findAll();
        createIndexWithCustomAnalyzer();
        for (RealEstate realEstate : realEstates) {
            RealEstateDto realEstateDto = convertRealEstateToDtoSearch(realEstate);
            eventPublisher.publishEvent(new RealEstateChangedEvent(realEstateDto));
        }
    }

    // Phương thức test index RealEstate
    public void indexTestRealEstate() {
        RealEstate realEstate = realEstateRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("RealEstate not found"));
        createIndexWithCustomAnalyzer();
        RealEstateDto realEstateDto = convertRealEstateToDtoSearch(realEstate);
        eventPublisher.publishEvent(new RealEstateChangedEvent(realEstateDto));
    }

    // Phương thức tìm kiếm RealEstate theo các tham số tìm kiếm
    public Page<RealEstateDto> search(RealEstateSearchParameters realEstateSearchParameters) {
        List<RealEstateDto> result = new ArrayList<>();
        try {
            // Create a request to Elasticsearch
            Request request = new Request("POST", "/realestate_index/_search");
            // Create a JSON object to store the search query
            ObjectMapper mapper = new ObjectMapper();
            // Create the root node
            ObjectNode rootNode = mapper.createObjectNode();
            // Add the size and from parameters to the root node
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
                // Get the search text
                String text = realEstateSearchParameters.getSearchText();
                // Set the query and the analyzers
                multiMatchNode.put("query", text);
                multiMatchNode.put("analyzer", "vietnamese_analyzer");// Use the custom analyzer
                // Add the fields to search in, and their weights. Formula is field^weight
                multiMatchNode.putArray("fields")
                        .add("title^1.0")
                        .add("description^0.5")
                        .add("address^2.0")
                        .add("keywords^1.0");
                // Set the type of the multi_match query
                multiMatchNode.put("type", "best_fields");
                multiMatchNode.put("fuzziness", "AUTO"); // Apply fuzzy search, You can set this to a specific value
                                                         // like "1", "2", etc.
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
            // Convert the JSON object to a string
            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            logger.info("Request {}", jsonString);
            request.setJsonEntity(jsonString);

            // Execute the request
            Response response = client.performRequest(request);
            // Parse the response
            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseBody);
            logger.info("responseBody {}", responseBody);
            JSONArray hits = jsonObject.getJSONObject("hits").getJSONArray("hits");
            // Get the total number of hits
            int totalHits = jsonObject.getJSONObject("hits").getJSONObject("total").getInt("value");
            // Convert each hit to a RealEstateDto and add it to the result list
            for (int i = 0; i < hits.length(); i++) {
                JSONObject hit = hits.getJSONObject(i);
                JSONObject source = hit.getJSONObject("_source");
                RealEstateDto realEstateDto = mapper.readValue(source.toString(), RealEstateDto.class);
                addPhotoUrls(realEstateDto);
                result.add(realEstateDto);
            }
            // Create a Page object from the result list
            int start = realEstateSearchParameters.getFrom() != null ? realEstateSearchParameters.getFrom() : 0;
            int size = realEstateSearchParameters.getSize() != null ? realEstateSearchParameters.getSize()
                    : result.size();
            if (size == 0) {
                return new PageImpl<>(result);
            }
            Pageable pageable = PageRequest.of(start / size, size);

            Page<RealEstateDto> page = new PageImpl<>(result, pageable, totalHits);
            // Return the page
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            return new PageImpl<>(result);
        }
    }

    // Phương thức tạo index với analyzer tùy chỉnh(vietnamese_analyzer)
    public void createIndexWithCustomAnalyzer() {
        try {
            // Tạo request để tạo index
            Request request = new Request("PUT", "/realestate_index");
            // Tạo JSON object để thiết lập cấu hình index
            ObjectMapper mapper = new ObjectMapper();
            // Tạo root node
            ObjectNode rootNode = mapper.createObjectNode();

            // Tạo và thêm settings node vào root node
            ObjectNode settingsNode = rootNode.putObject("settings");
            // Tạo và thêm analysis node vào settings node
            ObjectNode analysisNode = settingsNode.putObject("analysis");
            // Tạo và thêm analyzer node vào analysis node
            ObjectNode analyzerNode = analysisNode.putObject("analyzer");
            // Tạo và thêm vietnamese_analyzer node vào analyzer node
            ObjectNode vietnameseAnalyzerNode = analyzerNode.putObject("vietnamese_analyzer");
            // Thiết lập các thuộc tính cho vietnamese_analyzer
            // Sử dụng tokenizer standard
            vietnameseAnalyzerNode.put("tokenizer", "standard");
            // Tạo và thêm filter node vào vietnamese_analyzer
            ArrayNode filterNode = vietnameseAnalyzerNode.putArray("filter");
            // Add the vietnamese analyzer filters to the filter node (lowercase,
            // asciifolding)
            filterNode.add("lowercase");
            filterNode.add("asciifolding");
            // Tạo và thêm char_filter node vào analysis node
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

    // Phương thức chuyển đổi RealEstate thành RealEstateDto
    public RealEstateDto convertRealEstateToDtoSearch(RealEstate realEstate) {
        // Chuyển đổi RealEstate thành RealEstateDto
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

        if (realEstateDto.getType() != null) {
            // Danh mục tin đăng: 0.Đất, 1.Nhà ở,
            // 2.Căn hộ/Chung cư, 3.Văn phòng, Mặt bằng
            // 4.Kinh doanh, 5.Phòng trọ,
            // 6.Kho/Nhà xưởng
            // Khởi tạo danh sách keywords nếu chưa có
            if (realEstateDto.getKeywords() == null) {
                realEstateDto.setKeywords(new ArrayList<>());
            }
            switch (realEstateDto.getType()) {
                case 0:
                    realEstateDto.getKeywords().add("Đất");
                    break;
                case 1:
                    realEstateDto.getKeywords().add("Nhà ở");
                    break;
                case 2:
                    realEstateDto.getKeywords().add("Căn hộ/Chung cư");
                    break;
                case 3:
                    realEstateDto.getKeywords().add("Văn phòng");
                    realEstateDto.getKeywords().add("Mặt bằng");
                    break;
                case 4:
                    realEstateDto.getKeywords().add("Kinh doanh");
                    break;
                case 5:
                    realEstateDto.getKeywords().add("Phòng trọ");
                    break;
                case 6:
                    realEstateDto.getKeywords().add("Kho");
                    realEstateDto.getKeywords().add("Nhà xưởng");
                    break;
                default:
                    break;
            }
        }
        if (realEstateDto.getRequest() != null) {
            // Nhu cầu 0.Cần bán, 1.Cần mua, 2.Cho thuê, 3.Cần thuê
            // Khởi tạo danh sách keywords nếu chưa có
            if (realEstateDto.getKeywords() == null) {
                realEstateDto.setKeywords(new ArrayList<>());
            }
            switch (realEstateDto.getRequest()) {
                case 0:
                    realEstateDto.getKeywords().add("Cần bán");
                    break;
                case 1:
                    realEstateDto.getKeywords().add("Cần mua");
                    break;
                case 2:
                    realEstateDto.getKeywords().add("Cho thuê");
                    break;
                case 3:
                    realEstateDto.getKeywords().add("Cần thuê");
                    break;
                default:
                    break;
            }
        }
        if (realEstateDto.getDirection() != null) {
            // Hướng nhà, căn hộ Đông: 1, Tây: 2, Bắc: 3, Nam: 4
            // Đông Bắc: 5, Tây Bắc: 6, Đông Nam: 7, Tây Nam: 8
            // Khởi tạo danh sách keywords nếu chưa có
            if (realEstateDto.getKeywords() == null) {
                realEstateDto.setKeywords(new ArrayList<>());
            }
            switch (realEstateDto.getDirection()) {
                case 1:
                    realEstateDto.getKeywords().add("Đông");
                    break;
                case 2:
                    realEstateDto.getKeywords().add("Tây");
                    break;
                case 3:
                    realEstateDto.getKeywords().add("Bắc");
                    break;
                case 4:
                    realEstateDto.getKeywords().add("Nam");
                    break;
                case 5:
                    realEstateDto.getKeywords().add("Đông Bắc");
                    break;
                case 6:
                    realEstateDto.getKeywords().add("Tây Bắc");
                    break;
                case 7:
                    realEstateDto.getKeywords().add("Đông Nam");
                    break;
                case 8:
                    realEstateDto.getKeywords().add("Tây Nam");
                    break;
                default:
                    break;
            }
        }
        // Khai báo các đối tượng AddressDto, ProvinceDto, DistrictDto, WardDto,
        // StreetDto
        AddressDto addressDto = new AddressDto();
        ProvinceDto provinceDto = new ProvinceDto();
        DistrictDto districtDto = new DistrictDto();
        WardDto wardDto = new WardDto();
        StreetDto streetDto = new StreetDto();
        if (realEstate.getProvince() != null) {
            provinceDto.setId(realEstate.getProvince().getId());
            provinceDto.setName(realEstate.getProvince().getName());
            realEstateDto.getKeywords().add(realEstate.getProvince().getName());
            addressDto.setProvince(provinceDto);
        }
        if (realEstate.getDistrict() != null) {
            districtDto.setId(realEstate.getDistrict().getId());
            districtDto.setName(realEstate.getDistrict().getName());
            districtDto.setPrefix(realEstate.getDistrict().getPrefix());
            realEstateDto.getKeywords().add(realEstate.getDistrict().getName());
            addressDto.setDistrict(districtDto);
        }
        if (realEstate.getWard() != null) {
            wardDto.setId(realEstate.getWard().getId());
            wardDto.setName(realEstate.getWard().getName());
            wardDto.setPrefix(realEstate.getWard().getPrefix());
            addressDto.setWard(wardDto);
        }
        if (realEstate.getStreet() != null) {
            streetDto.setId(realEstate.getStreet().getId());
            streetDto.setName(realEstate.getStreet().getName());
            realEstateDto.getKeywords().add(realEstate.getStreet().getName());
            addressDto.setStreet(streetDto);
        }
        addressDto.setProvince(provinceDto);
        addressDto.setDistrict(districtDto);
        addressDto.setWard(wardDto);
        addressDto.setStreet(streetDto);
        realEstateDto.setAddressDetail(addressDto);
        // Thêm thông tin khách hàng vào RealEstateDto
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

    // Phương thức thêm photoUrls vào RealEstateDto
    public void addPhotoUrls(RealEstateDto realEstateDto) {
        List<String> photoUrls = photoService.getUrlsByRealEstateId(realEstateDto.getId());
        realEstateDto.setPhotoUrls(photoUrls);
    }
}
