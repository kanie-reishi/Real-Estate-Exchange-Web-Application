package devcamp.realestateexchange.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.realestate.MasterLayoutDto;
import devcamp.realestateexchange.services.realestate.MasterLayoutService;

@RestController
@CrossOrigin
@RequestMapping
public class MasterLayoutController {
    @Autowired
    MasterLayoutService masterLayoutService;

    // Get all master layouts
    @GetMapping("/masterLayouts")
    public ResponseEntity<Object> getMasterLayouts(Pageable pageable) {
        try {
            return ResponseEntity.ok(masterLayoutService.getMasterLayouts(pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get master layout by id
    @GetMapping("/masterLayouts/{id}")
    public ResponseEntity<Object> getMasterLayoutById(Integer id) {
        try {
            return ResponseEntity.ok(masterLayoutService.getMasterLayoutById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * @GetMapping("/masterLayouts/{id}/apartments")
     * public ResponseEntity<Object> getApartmentsByMasterLayoutId(Integer id) {
     * try {
     * return
     * ResponseEntity.ok(masterLayoutService.getApartmentsByMasterLayoutId(id));
     * } catch (Exception e) {
     * return ResponseEntity.badRequest().body(e.getMessage());
     * }
     * }
     */
    // Create new master layout
    @PostMapping("/masterLayouts")
    public ResponseEntity<Object> saveMasterLayout(MasterLayoutDto masterLayoutDto) {
        try {
            return ResponseEntity.ok(masterLayoutService.saveMasterLayout(masterLayoutDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete master layout
    @DeleteMapping("/masterLayouts/{id}")
    public ResponseEntity<Object> deleteMasterLayout(Integer id) {
        try {
            masterLayoutService.deleteMasterLayout(id);
            return ResponseEntity.ok("Master layout deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update master layout
    @PutMapping("/masterLayouts")
    public ResponseEntity<Object> updateMasterLayout(MasterLayoutDto masterLayoutDto) {
        try {
            return ResponseEntity.ok(masterLayoutService.saveMasterLayout(masterLayoutDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
