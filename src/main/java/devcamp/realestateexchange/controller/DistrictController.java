package devcamp.realestateexchange.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.location.DistrictDto;
import devcamp.realestateexchange.entity.location.District;
import devcamp.realestateexchange.services.location.DistrictService;

@RestController
@CrossOrigin
@RequestMapping
public class DistrictController {
    @Autowired
    private DistrictService districtService;
    @Autowired
    private ModelMapper modelMapper;

    // get all districts
    @GetMapping("/districts")
    public ResponseEntity<Object> getDistrictList(Pageable pageable) {
        try {
            Page<DistrictDto> districtPage = districtService.getDistrictList(pageable);
            return ResponseEntity.ok(districtPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // get District by id
    @GetMapping("/districts/{id}")
    public ResponseEntity<Object> getDistrictById(@PathVariable Integer id) {
        try {
            DistrictDto district = districtService.getDistrictById(id);
            return ResponseEntity.ok(district);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get all districts by province id
    @GetMapping("/provinces/{provinceId}/districts")
    public ResponseEntity<Object> getDistrictListByProvinceId(Pageable pageable, @PathVariable Integer provinceId) {
        try {
            Page<DistrictDto> districtPage = districtService.getDistrictListByProvinceId(pageable, provinceId);
            return ResponseEntity.ok(districtPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // delete district by id
    @DeleteMapping("/districts/{id}")
    public ResponseEntity<Object> deleteDistrictById(@PathVariable Integer id) {
        try {
            districtService.deleteDistrictById(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Create new district
    @PostMapping("/districts")
    public ResponseEntity<Object> saveDistrict(@RequestBody DistrictDto districtDto) {
        try {
            District district = modelMapper.map(districtDto, District.class);
            district = districtService.saveDistrict(district);
            return ResponseEntity.ok(district);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update district
    @PutMapping("/districts")
    public ResponseEntity<Object> updateDistrict(@RequestBody DistrictDto districtDto) {
        try {
            District district = modelMapper.map(districtDto, District.class);
            district = districtService.saveDistrict(district);
            return ResponseEntity.ok(district);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
