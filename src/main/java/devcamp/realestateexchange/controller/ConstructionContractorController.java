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

import devcamp.realestateexchange.dto.realestate.ContractorDto;
import devcamp.realestateexchange.entity.realestate.ConstructionContractor;
import devcamp.realestateexchange.services.realestate.ConstructionContractorService;

@RestController
@CrossOrigin
@RequestMapping
public class ConstructionContractorController {
    @Autowired
    private ConstructionContractorService constructionContractorService;

    @GetMapping("/constructionContractors/{id}")
    public ResponseEntity<Object> getConstructionContractorById(@PathVariable Integer id){
        try {
            ContractorDto constructionContractor = constructionContractorService.getConstructionContractorById(id);
            return ResponseEntity.ok(constructionContractor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/constructionContractors")
    public ResponseEntity<Object> getConstructionContractors(Pageable pageable){
        try {
            Page<ContractorDto> constructionContractorPage = constructionContractorService.getConstructionContractors(pageable);
            return ResponseEntity.ok(constructionContractorPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/projects/{projectId}/constructionContractors")
    public ResponseEntity<Object> getConstructionContractorListByProjectId(Pageable pageable, @PathVariable Integer projectId){
        try {
            List<ContractorDto> constructionContractorPage = constructionContractorService.getConstructionContractorsByProjectId(projectId);
            return ResponseEntity.ok(constructionContractorPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/constructionContractors")
    public ResponseEntity<Object> saveConstructionContractor(@RequestBody ContractorDto contractorDto){
        try {
            ConstructionContractor constructionContractor = constructionContractorService.saveConstructionContractor(contractorDto);
            return ResponseEntity.ok(constructionContractor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/constructionContractors")
    public ResponseEntity<Object> updateConstructionContractor(@RequestBody ContractorDto contractorDto){
        try {
            ConstructionContractor constructionContractor = constructionContractorService.saveConstructionContractor(contractorDto);
            return ResponseEntity.ok(constructionContractor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/constructionContractors/{id}")
    public ResponseEntity<Object> deleteConstructionContractor(@PathVariable Integer id){
        try {
            constructionContractorService.deleteConstructionContractor(id);
            return ResponseEntity.ok("Construction Contractor deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
