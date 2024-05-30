package devcamp.realestateexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import devcamp.realestateexchange.services.RealEstateService;
import devcamp.realestateexchange.dto.RealEstateDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
@RestController
@CrossOrigin
@RequestMapping
public class RealEstateController {
    @Autowired
    private RealEstateService realEstateService;
    @GetMapping("/realestate")
    public ResponseEntity<Object> getRealEstateList(Pageable pageable){
        try {
            Page<RealEstateDto> realEstatePage = realEstateService.getRealEstateList(pageable);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
