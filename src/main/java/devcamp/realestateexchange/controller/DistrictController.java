package devcamp.realestateexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.DistrictDto;
import devcamp.realestateexchange.services.DistrictService;
@RestController
@CrossOrigin
@RequestMapping
public class DistrictController {
    @Autowired
    private DistrictService districtService;
    @GetMapping("/district/{id}")
    public ResponseEntity<Object> getDistrictById(@PathVariable Integer id){
        try {
            DistrictDto district = districtService.getDistrictById(id);
            return ResponseEntity.ok(district);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
