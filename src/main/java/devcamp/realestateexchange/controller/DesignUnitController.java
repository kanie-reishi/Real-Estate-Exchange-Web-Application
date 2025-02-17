package devcamp.realestateexchange.controller;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import devcamp.realestateexchange.dto.realestate.DesignUnitDto;
import devcamp.realestateexchange.entity.realestate.DesignUnit;
import devcamp.realestateexchange.services.realestate.DesignUnitService;

@RestController
@CrossOrigin
@RequestMapping
public class DesignUnitController {
    @Autowired
    private DesignUnitService designUnitService;
    // Get design unit by id
    @GetMapping("/design-units/{id}")
    public ResponseEntity<Object> getDesignUnitById(@PathVariable Integer id){
        try {
            DesignUnitDto designUnit = designUnitService.getDesignUnitById(id);
            return ResponseEntity.ok(designUnit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Get all design units
    @GetMapping("/design-units")
    public ResponseEntity<Object> getDesignUnits(Pageable pageable){
        try {
            Page<DesignUnitDto> designUnitPage = designUnitService.getAllDesignUnit(pageable);
            return ResponseEntity.ok(designUnitPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Create new design unit
    @PostMapping("/design-units")
    public ResponseEntity<Object> saveDesignUnit(@RequestBody DesignUnitDto designUnitDto){
        try {
            DesignUnit designUnit = designUnitService.saveDesignUnit(designUnitDto);
            return ResponseEntity.ok(designUnit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Get design unit by project id
    @GetMapping("/projects/{projectId}/design-units")
    public ResponseEntity<Object> getDesignUnitListByProjectId(Pageable pageable, @PathVariable Integer projectId){
        try {
            List<DesignUnitDto> designUnitPage = designUnitService.getDesignUnitByProjectId(projectId);
            return ResponseEntity.ok(designUnitPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Update design unit
    @PutMapping("/design-units")
    public ResponseEntity<Object> updateDesignUnit(@RequestBody DesignUnitDto designUnitDto){
        try {
            DesignUnit designUnit = designUnitService.saveDesignUnit(designUnitDto);
            return ResponseEntity.ok(designUnit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Delete design unit
    @DeleteMapping("/design-units/{id}")
    public ResponseEntity<Object> deleteDesignUnit(@PathVariable Integer id){
        try {
            designUnitService.deleteDesignUnit(id);
            return ResponseEntity.ok("Design unit deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Get design unit table page
    @GetMapping("/admin/design-units")
    public ModelAndView getDesignUnitAdminPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("design-unit-table");
        return modelAndView;
    }
}
