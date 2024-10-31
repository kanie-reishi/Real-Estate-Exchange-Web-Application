package devcamp.realestateexchange.controller;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.location.ProvinceDto;
import devcamp.realestateexchange.dto.realestate.ProjectDto;
import devcamp.realestateexchange.entity.location.Province;
import devcamp.realestateexchange.models.ProjectSearchParameters;
import devcamp.realestateexchange.services.location.ProvinceService;
import devcamp.realestateexchange.services.realestate.ProjectService;

@RestController
@CrossOrigin
@RequestMapping
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private ModelMapper modelMapper;

    // get Province by id
    @GetMapping("/provinces/{id}")
    public ResponseEntity<Object> getProvinceById(@PathVariable Integer id) {
        try {
            ProvinceDto province = provinceService.getProvinceDtoById(id);
            return ResponseEntity.ok(province);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // get all provinces
    @GetMapping("/provinces")
    public ResponseEntity<Object> getProvinceList() {
        try {
            return ResponseEntity.ok(provinceService.getProvinceList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Create a new province
    @PostMapping("/provinces")
    public ResponseEntity<Object> createProvince(@RequestBody ProvinceDto provinceDto) {
        try {
            Province province = modelMapper.map(provinceDto, Province.class);
            province = provinceService.saveProvince(province);
            return ResponseEntity.ok(province);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update a province
    @PutMapping("/provinces")
    public ResponseEntity<Object> updateProvince(@RequestBody ProvinceDto provinceDto) {
        try {
            Province province = modelMapper.map(provinceDto, Province.class);
            province = provinceService.saveProvince(province);
            return ResponseEntity.ok(province);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete a province
    @DeleteMapping("/provinces/{id}")
    public ResponseEntity<Object> deleteProvince(@PathVariable Integer id) {
        try {
            provinceService.deleteProvinceById(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
