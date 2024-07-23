package devcamp.realestateexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
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

    @GetMapping("/realestate/{id}")
    public ResponseEntity<Object> getRealEstateById(@PathVariable Integer id){
        try {
            RealEstateDto realEstate = realEstateService.getRealEstateDtoById(id);
            return ResponseEntity.ok(realEstate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/realestate/search")
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
    @PostMapping("/realestate")
    public ResponseEntity<Object> createRealEstate(@RequestBody RealEstateDto realEstateDto) {
        try {
            realEstateService.saveRealEstate(realEstateDto);
            return ResponseEntity.ok("Real estate created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
