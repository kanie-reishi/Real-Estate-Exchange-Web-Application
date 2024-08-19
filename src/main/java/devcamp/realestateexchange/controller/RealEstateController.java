package devcamp.realestateexchange.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.models.RealEstateSearchParameters;
import devcamp.realestateexchange.services.realestate.RealEstateService;

@RestController
@CrossOrigin
@RequestMapping
public class RealEstateController {
    @Autowired
    private RealEstateService realEstateService;

    @GetMapping("/realestate")
    public ResponseEntity<Object> getRealEstateList(Pageable pageable) {
        try {
            Page<RealEstateDto> realEstatePage = realEstateService.getAllRealEstateDtos(null, pageable);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * @GetMapping("/realestate/{id}")
     * public ResponseEntity<Object> getRealEstateById(@PathVariable Integer id){
     * try {
     * RealEstateDto realEstate = realEstateService.getRealEstateDtoById(id);
     * return ResponseEntity.ok(realEstate);
     * } catch (Exception e) {
     * return ResponseEntity.badRequest().body(e.getMessage());
     * }
     * }
     */
    @GetMapping("/realestate/table")
    public ResponseEntity<Object> getRealEstateTable(
            @RequestParam Map<String, String> allRequestParams) {
        try {
            Integer draw = Integer.parseInt(allRequestParams.get("draw"));
            Integer start = Integer.parseInt(allRequestParams.get("start"));
            Integer length = Integer.parseInt(allRequestParams.get("length"));
            String orderColumnStr = allRequestParams.get("order[0][column]");
            Integer orderColumn = orderColumnStr != null ? Integer.parseInt(allRequestParams.get("order[0][column]")) : null;
            String orderDir = allRequestParams.get("order[0][dir]");
            String searchTerm = allRequestParams.get("search[value]");
            Boolean searchRegex = Boolean.parseBoolean(allRequestParams.get("search[regex]"));
            // Define sort column
            String[] columns = new String[] { "feature", "realEstateCode", "province", "district", "ward", "street",
                    "type", "request", "customer", "price", "createdAt", "acreage", "priceTime", "images" };
            // Check if the column is orderable
            String orderableKey = "columns[" + orderColumn + "][orderable]";
            String orderable = allRequestParams.get(orderableKey);
            if ("true".equals(orderable) && orderColumn != null) {
                // Define sort direction
                Sort.Direction direction = Sort.Direction.fromString(orderDir);
                Sort sort = Sort.by(direction, columns[orderColumn]);

                Pageable pageable = PageRequest.of(start / length, length, sort);
                Page<RealEstateDto> realEstatePage = realEstateService.getAllRealEstateDtos(searchTerm, pageable);

                // Create response object
                Map<String, Object> response = new HashMap<>();
                response.put("draw", draw);
                response.put("recordsTotal", realEstatePage.getTotalElements());
                response.put("recordsFiltered", realEstatePage.getTotalElements());
                response.put("data", realEstatePage.getContent());

                return ResponseEntity.ok(response);
            }
            Pageable pageable = PageRequest.of(start / length, length);
            Page<RealEstateDto> realEstatePage = realEstateService.getAllRealEstateDtos(searchTerm, pageable);

            // Create response object
            Map<String, Object> response = new HashMap<>();
            response.put("draw", draw);
            response.put("recordsTotal", realEstatePage.getTotalElements());
            response.put("recordsFiltered", realEstatePage.getTotalElements());
            response.put("data", realEstatePage.getContent());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/realestate/search")
    public ResponseEntity<Object> searchRealEstates(@RequestBody RealEstateSearchParameters realEstateSearchParameters) {
        try {
            realEstateService.search(realEstateSearchParameters);
            return ResponseEntity.ok("Search Real Estate successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/realestate")
    public ResponseEntity<Object> createRealEstate(@RequestBody RealEstateDto realEstateDto) {
        try {
            realEstateService.saveRealEstate(realEstateDto);
            return ResponseEntity.ok("Real estate created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/realestate/indexall")
    public ResponseEntity<Object> indexAllRealEstates(){
        try{
            realEstateService.indexAllRealEstates();
            return ResponseEntity.ok("Index All Real Estate successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
