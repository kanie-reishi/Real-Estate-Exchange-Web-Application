package devcamp.realestateexchange.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.realestate.RegionLinkDto;
import devcamp.realestateexchange.services.realestate.RegionLinkService;
@RestController
@CrossOrigin
@RequestMapping
public class RegionLinkController {
    @Autowired
    private RegionLinkService regionLinkService;
    @GetMapping("/regionLinks")
    public ResponseEntity<Object> getRegionLinks(Pageable pageable) {
        try {
            return ResponseEntity.ok(regionLinkService.getRegionLinks(pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/regionLinks/{id}")
    public ResponseEntity<Object> getRegionLinkById(Integer id) {
        try {
            return ResponseEntity.ok(regionLinkService.getRegionLinkById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/regionLinks")
    public ResponseEntity<Object> saveRegionLink(RegionLinkDto regionLinkDto) {
        try {
            return ResponseEntity.ok(regionLinkService.saveRegionLink(regionLinkDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/regionLinks/{id}")
    public ResponseEntity<Object> deleteRegionLink(Integer id) {
        try {
            regionLinkService.deleteRegionLink(id);
            return ResponseEntity.ok("Region link deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/projects/{projectId}/regionLinks")
    public ResponseEntity<Object> getRegionLinksByProjectId(@PathVariable Integer projectId) {
        try {
            return ResponseEntity.ok(regionLinkService.getRegionLinksByProjectId(projectId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/regionLinks")
    public ResponseEntity<Object> updateRegionLink(RegionLinkDto regionLinkDto) {
        try {
            return ResponseEntity.ok(regionLinkService.saveRegionLink(regionLinkDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
