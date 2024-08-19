package devcamp.realestateexchange.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            Page<RealEstateDto> realEstatePage = realEstateService.getAllRealEstateDtos(pageable);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/realestate/table")
    public ResponseEntity<Object> getRealEstateTable(
            @RequestParam Map<String, String> allRequestParams) {
        try {
            Integer draw = Integer.parseInt(allRequestParams.get("draw") != null ? allRequestParams.get("draw") : "0");
            Integer start = Integer
                    .parseInt(allRequestParams.get("start") != null ? allRequestParams.get("start") : "0");
            Integer length = Integer
                    .parseInt(allRequestParams.get("length") != null ? allRequestParams.get("length") : "10");
            String orderColumnStr = allRequestParams.get("order[0][column]");
            Integer orderColumn = orderColumnStr != null ? Integer.parseInt(allRequestParams.get("order[0][column]"))
                    : null;
            String orderDir = allRequestParams.get("order[0][dir]");
            String searchTerm = allRequestParams.get("search[value]");
            Boolean searchRegex = Boolean.parseBoolean(allRequestParams.get("search[regex]") != null
                    ? allRequestParams.get("search[regex]")
                    : "false");

            // Define sort column
            String[] columns = new String[] { "", "realEstateCode", "addressDetail.province.id", "addressDetail.district.id", "addressDetail.ward.id", "addressDetail.street.id",
                    "address", "title", "description", "type", "request", "customer.id", "price", "createdAt", "acreage", "priceTime", "images" };
            // Check if the column is orderable
            String orderableKey = "columns[" + orderColumn + "][orderable]";
            String orderable = allRequestParams.get(orderableKey);

            RealEstateSearchParameters realEstateSearchParameters = new RealEstateSearchParameters();
            realEstateSearchParameters.setSearchText(searchTerm);
            realEstateSearchParameters.setSize(length);
            realEstateSearchParameters.setFrom(start);
            if (orderColumn != null && orderColumn >= 0 && orderColumn < columns.length && orderable.equals("true") && orderColumnStr != null && orderDir != null) {
                realEstateSearchParameters.setSort(columns[orderColumn]);
                realEstateSearchParameters.setOrder(orderDir);
            } else {
                realEstateSearchParameters.setSort(null); // Default sort field
                realEstateSearchParameters.setOrder(null); // Default sort order
            }
            Page<RealEstateDto> realEstatePage = realEstateService.search(realEstateSearchParameters);

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
    public ResponseEntity<Object> searchRealEstates(
            @RequestBody RealEstateSearchParameters realEstateSearchParameters) {
        try {
            Page<RealEstateDto> realEstateDtos = realEstateService.search(realEstateSearchParameters);
            return ResponseEntity.ok(realEstateDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/realestate/{id}")
    public ResponseEntity<Object> getRealEstateById(@RequestParam Integer id) {
        try {
            RealEstate realEstate = realEstateService.getRealEstateById(id);
            return ResponseEntity.ok(realEstate);
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
    @PutMapping("/realestate")
    public ResponseEntity<Object> updateRealEstate(@RequestBody RealEstateDto realEstateDto) {
        try {
            realEstateService.saveRealEstate(realEstateDto);
            return ResponseEntity.ok("Real estate updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/realestate")
    public ResponseEntity<Object> deleteRealEstate(@RequestParam Integer id) {
        try {
            realEstateService.deleteRealEstateById(id);
            return ResponseEntity.ok("Real estate deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/realestate/indexall")
    public ResponseEntity<Object> indexAllRealEstates() {
        try {
            realEstateService.indexAllRealEstates();
            return ResponseEntity.ok("Index All Real Estate successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/realestate/indextest")
    public ResponseEntity<Object> indexTestRealEstate() {
        try {
            realEstateService.indexTestRealEstate();
            return ResponseEntity.ok("Index Test Real Estate successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
