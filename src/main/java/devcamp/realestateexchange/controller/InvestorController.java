package devcamp.realestateexchange.controller;

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

import devcamp.realestateexchange.dto.realestate.InvestorDto;
import devcamp.realestateexchange.entity.realestate.Investor;
import devcamp.realestateexchange.services.realestate.InvestorService;

@RestController
@CrossOrigin
@RequestMapping
public class InvestorController {
    @Autowired
    private InvestorService investorService;

    // Get all investors
    @GetMapping("/investors")
    public ResponseEntity<Object> getInvestors(Pageable pageable) {
        try {
            Page<InvestorDto> investorList = investorService.getInvestors(pageable);
            return ResponseEntity.ok(investorList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get investor by id
    @GetMapping("/investors/{id}")
    public ResponseEntity<Object> getInvestorById(@PathVariable Integer id) {
        try {
            InvestorDto investor = investorService.getInvestorById(id);
            return ResponseEntity.ok(investor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Create new investor
    @PostMapping("/investors")
    public ResponseEntity<Object> saveInvestor(@RequestBody InvestorDto investorDto) {
        try {
            Investor investor = investorService.saveInvestor(investorDto);
            return ResponseEntity.ok(investor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get investor by project id
    @GetMapping("/projects/{projectId}/investors")
    public ResponseEntity<Object> getInvestorsByProjectId(@PathVariable Integer projectId) {
        try {
            return ResponseEntity.ok(investorService.getInvestorsByProjectId(projectId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update investor
    @PutMapping("/investors")
    public ResponseEntity<Object> updateInvestor(@RequestBody InvestorDto investorDto) {
        try {
            Investor investor = investorService.saveInvestor(investorDto);
            return ResponseEntity.ok(investor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete investor
    @DeleteMapping("/investors/{id}")
    public ResponseEntity<Object> deleteInvestor(@PathVariable Integer id) {
        try {
            investorService.deleteInvestor(id);
            return ResponseEntity.ok("Investor deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
