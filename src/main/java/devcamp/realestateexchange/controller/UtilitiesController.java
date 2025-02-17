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
import org.springframework.web.servlet.ModelAndView;

import devcamp.realestateexchange.dto.realestate.UtilitiesDto;
import devcamp.realestateexchange.services.realestate.UtilitiesService;

@RestController
@CrossOrigin
@RequestMapping
public class UtilitiesController {
    @Autowired
    private UtilitiesService utilitiesService;

    // Get all utilities
    @GetMapping("/utilities")
    public ResponseEntity<Object> getUtilities(Pageable pageable) {
        try {
            return ResponseEntity.ok(utilitiesService.getUtilities(pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get utility by id
    @GetMapping("/utilities/{id}")
    public ResponseEntity<Object> getUtilityById(Integer id) {
        try {
            return ResponseEntity.ok(utilitiesService.getUtilityById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Create new utility
    @PostMapping("/utilities")
    public ResponseEntity<Object> saveUtility(UtilitiesDto utilityDto) {
        try {
            return ResponseEntity.ok(utilitiesService.saveUtility(utilityDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete utility
    @DeleteMapping("/utilities/{id}")
    public ResponseEntity<Object> deleteUtility(Integer id) {
        try {
            utilitiesService.deleteUtility(id);
            return ResponseEntity.ok("Utility deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*
     * @GetMapping("/projects/{projectId}/utilities")
     * public ResponseEntity<Object> getUtilitiesByProjectId(@PathVariable Integer
     * projectId) {
     * try {
     * return
     * ResponseEntity.ok(utilitiesService.getUtilitiesByProjectId(projectId));
     * } catch (Exception e) {
     * return ResponseEntity.badRequest().body(e.getMessage());
     * }
     * }
     */
    // Update utility
    @PutMapping("/utilities")
    public ResponseEntity<Object> updateUtility(UtilitiesDto utilityDto) {
        try {
            return ResponseEntity.ok(utilitiesService.saveUtility(utilityDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Get utilities table page
    @GetMapping("/admin/utilities")
    public ModelAndView getUtilityAdminPage() {
        return new ModelAndView("utilities-table");
    }
}
