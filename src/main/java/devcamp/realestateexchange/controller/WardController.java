package devcamp.realestateexchange.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.location.WardDto;
import devcamp.realestateexchange.services.location.WardService;
@RestController
@CrossOrigin
@RequestMapping
public class WardController {
    @Autowired
    private WardService wardService;
    @GetMapping("/wards/{id}")
    public ResponseEntity<Object> getWardById(@PathVariable Integer id){
        try {
            WardDto ward = wardService.getWardById(id);
            return ResponseEntity.ok(ward);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/districts/{districtId}/wards")
    public ResponseEntity<Object> getWardListByDistrictId(Pageable pageable, @PathVariable Integer districtId){
        try {
            Page<WardDto> wardPage = wardService.getWardListByDistrictId(pageable, districtId);
            return ResponseEntity.ok(wardPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
