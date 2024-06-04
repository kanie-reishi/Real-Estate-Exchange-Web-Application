package devcamp.realestateexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.RealEstateDto;
import devcamp.realestateexchange.models.RealEstateSearchParameters;
import devcamp.realestateexchange.services.RealEstateService;

@RestController
@CrossOrigin
@RequestMapping
public class RealEstateController {
    @Autowired
    private RealEstateService realEstateService;

    @GetMapping("/realestate")
    public ResponseEntity<Object> getRealEstateList(Pageable pageable) {
        try {
            Page<RealEstateDto> realEstatePage = realEstateService.getRealEstateList(pageable);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/realestate/viewcount")
    public ResponseEntity<Object> getRealEstateListOrderByViewCount(Pageable pageable) {
        try {
            Page<RealEstateDto> realEstatePage = realEstateService.getRealEstateListOrderByViewCount(pageable);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/realestate/province")
    public ResponseEntity<Object> getRealEstateListFilterByProvinceId(Pageable pageable,
            @RequestParam Integer provinceId) {
        try {
            Page<RealEstateDto> realEstatePage = realEstateService.getRealEstateListFilterByProvinceId(pageable,
                    provinceId);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/realestate/district")
    public ResponseEntity<Object> getRealEstateListFilterByDistrictId(Pageable pageable,
            @RequestParam Integer districtId) {
        try {
            Page<RealEstateDto> realEstatePage = realEstateService.getRealEstateListFilterByDistrictId(pageable,
                    districtId);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/realestate/price")
    public ResponseEntity<Object> getRealEstateFilterByMinPriceAndMaxPrice(Pageable pageable,
            @RequestParam Double minPrice, @RequestParam Double maxPrice) {
        try {
            Page<RealEstateDto> realEstatePage = realEstateService.getRealEstateFilterByMinPriceAndMaxPrice(pageable,
                    minPrice, maxPrice);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/realestate/acreage")
    public ResponseEntity<Object> getRealEstateFilterByAcreage(Pageable pageable, @RequestParam Double minAcreage,
            @RequestParam Double maxAcreage) {
        try {
            Page<RealEstateDto> realEstatePage = realEstateService.getRealEstateFilterByAcreage(pageable, minAcreage,
                    maxAcreage);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/realestate/bedroom")
    public ResponseEntity<Object> getRealEstateFilterByBedroom(Pageable pageable, @RequestParam Integer bedroom) {
        try {
            Page<RealEstateDto> realEstatePage = realEstateService.getRealEstateFilterByBedroom(pageable, bedroom);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/realestate/search")
    public ResponseEntity<Object> searchRealEstates(@RequestBody RealEstateSearchParameters parameters, Pageable pageable) {
        try {
            Page<RealEstateDto> realEstatePage = realEstateService.searchRealEstates(
                    parameters.getProvinceId(),
                    parameters.getDistrictId(),
                    parameters.getMinPrice(),
                    parameters.getMaxPrice(),
                    parameters.getMinAcreage(),
                    parameters.getMaxAcreage(),
                    parameters.getBedroom(),
                    parameters.getAddress(),
                    pageable);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
