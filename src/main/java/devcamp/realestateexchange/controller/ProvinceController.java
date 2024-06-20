package devcamp.realestateexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.ProvinceDto;
import devcamp.realestateexchange.services.ProvinceService;
@RestController
@CrossOrigin
@RequestMapping
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;
    @GetMapping("/provinces/{id}")
    public ResponseEntity<Object> getProvinceById(@PathVariable Integer id){
        try {
            ProvinceDto province = provinceService.getProvinceDtoById(id);
            return ResponseEntity.ok(province);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/provinces")
    public ResponseEntity<Object> getProvinceList(){
        try {
            return ResponseEntity.ok(provinceService.getProvinceList());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
