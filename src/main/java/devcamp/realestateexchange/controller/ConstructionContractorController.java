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

import devcamp.realestateexchange.dto.realestate.ContractorDto;
import devcamp.realestateexchange.entity.realestate.ConstructionContractor;
import devcamp.realestateexchange.projections.ContractorProjection;
import devcamp.realestateexchange.services.realestate.ConstructionContractorService;

@RestController
@CrossOrigin
@RequestMapping
public class ConstructionContractorController {
    @Autowired
    private ConstructionContractorService constructionContractorService;
    // Get construction contractor by id
    @GetMapping("/construction-contractors/{id}")
    public ResponseEntity<Object> getConstructionContractorById(@PathVariable Integer id){
        try {
            ConstructionContractor constructionContractor = constructionContractorService.getConstructionContractorById(id);
            return ResponseEntity.ok(constructionContractor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Get all construction contractors
    @GetMapping("/construction-contractors")
    public ResponseEntity<Object> getAllConstructionContractors(Pageable pageable){
        try {
            Page<ConstructionContractor> constructionContractorList = constructionContractorService.getConstructionContractorsAll(pageable);
            return ResponseEntity.ok(constructionContractorList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Get construction contractor by project id
    @GetMapping("/projects/{projectId}/construction-contractors")
    public ResponseEntity<Object> getConstructionContractorListByProjectId(Pageable pageable, @PathVariable Integer projectId){
        try {
            List<ContractorDto> constructionContractorPage = constructionContractorService.getConstructionContractorsByProjectId(projectId);
            return ResponseEntity.ok(constructionContractorPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Create new construction contractor
    @PostMapping("/construction-contractors")
    public ResponseEntity<Object> saveConstructionContractor(@RequestBody ContractorDto contractorDto){
        try {
            ConstructionContractor constructionContractor = constructionContractorService.saveConstructionContractor(contractorDto);
            return ResponseEntity.ok(constructionContractor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Update construction contractor
    @PutMapping("/construction-contractors")
    public ResponseEntity<Object> updateConstructionContractor(@RequestBody ContractorDto contractorDto){
        try {
            ConstructionContractor constructionContractor = constructionContractorService.saveConstructionContractor(contractorDto);
            return ResponseEntity.ok(constructionContractor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Delete construction contractor
    @DeleteMapping("/construction-contractors/{id}")
    public ResponseEntity<Object> deleteConstructionContractor(@PathVariable Integer id){
        try {
            constructionContractorService.deleteConstructionContractor(id);
            return ResponseEntity.ok("Construction Contractor deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get construction contractor table page
    @GetMapping("/admin/construction-contractors")
    public ModelAndView getConstructionContractorAdminPage(){
        ModelAndView modelAndView = new ModelAndView("construction-contractors-table");
        return modelAndView;
    }
}
