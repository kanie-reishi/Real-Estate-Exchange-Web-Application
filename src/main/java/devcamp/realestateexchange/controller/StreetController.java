package devcamp.realestateexchange.controller;
import devcamp.realestateexchange.dto.location.StreetDto;
import devcamp.realestateexchange.services.location.StreetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
@RestController
@CrossOrigin
@RequestMapping
public class StreetController {
    @Autowired
    private StreetService streetService;
    @GetMapping("/streets/{id}")
    public ResponseEntity<Object> getStreetById(@PathVariable Integer id){
        try {
            StreetDto street = streetService.getStreetById(id);
            return ResponseEntity.ok(street);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/districts/{districtId}/streets")
    public ResponseEntity<Object> getStreetListByWardId(Pageable pageable, @PathVariable Integer districtId){
        try {
            Page<StreetDto> streetList = streetService.getStreetListByWardId(pageable, districtId);
            return ResponseEntity.ok(streetList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
