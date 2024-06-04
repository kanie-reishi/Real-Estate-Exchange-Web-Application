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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @GetMapping("/district/province/{provinceId}")
    public ResponseEntity<Object> getDistrictListByProvinceId(Pageable pageable, @PathVariable Integer provinceId){
        try {
            Page<DistrictDto> districtPage = districtService.getDistrictListByProvinceId(pageable, provinceId);
            return ResponseEntity.ok(districtPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
