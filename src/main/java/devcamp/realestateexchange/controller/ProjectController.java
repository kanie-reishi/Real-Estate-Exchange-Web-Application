package devcamp.realestateexchange.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import devcamp.realestateexchange.dto.realestate.ProjectDto;
import devcamp.realestateexchange.models.ProjectSearchParameters;
import devcamp.realestateexchange.services.realestate.ProjectService;
@RestController
@CrossOrigin
@RequestMapping
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    // Get project by id @param id @return ProjectDto
    @GetMapping("/projects/{id}")
    public ResponseEntity<Object> getProjectById(@PathVariable Integer id){
        try {
            ProjectDto project = projectService.getProjectById(id);
            return ResponseEntity.ok(project);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Get all projects @return Page<ProjectDto>
    @GetMapping("/projects")
    public ResponseEntity<Object> getProjectList(Pageable pageable){
        try {
            Page<ProjectDto> projectList = projectService.getAllsProjectDto(pageable);
            return ResponseEntity.ok(projectList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Get project table for datatable @param allRequestParams @return ResponseEntity<Object>
    @GetMapping("/projects/table")
    public ResponseEntity<Object> getProjectTable(
            @RequestParam Map<String, String> allRequestParams) {
        try {
            // get request parameters
            Integer draw = Integer.parseInt(allRequestParams.get("draw") != null ? allRequestParams.get("draw") : "0");
            Integer start = Integer
                    .parseInt(allRequestParams.get("start") != null ? allRequestParams.get("start") : "0");
            Integer length = Integer
                    .parseInt(allRequestParams.get("length") != null ? allRequestParams.get("length") : "10");
            String orderColumnStr = allRequestParams.get("order[0][column]");
            Integer orderColumn = orderColumnStr != null ? Integer.parseInt(allRequestParams.get("order[0][column]"))
                    : null;
            String orderDir = allRequestParams.get("order[0][dir]");
            String searchTerm = allRequestParams.get("search[value]");
            Boolean searchRegex = Boolean.parseBoolean(allRequestParams.get("search[regex]") != null
                    ? allRequestParams.get("search[regex]")
                    : "false");
            String orderableKey = "columns[" + orderColumn + "][orderable]";
            String orderable = allRequestParams.get(orderableKey);
            // Define sort column
            String[] columns = null;
            // Define search parameters
            ProjectSearchParameters searchParameters = new ProjectSearchParameters();
            searchParameters.setSearchText(searchTerm);
            searchParameters.setSize(length);
            searchParameters.setFrom(start);
            // Check if the column is orderable
            if (orderColumn != null && orderColumn >= 0 && orderColumn < columns.length && orderable.equals("true")
                    && orderColumnStr != null && orderDir != null) {
                searchParameters.setSort(columns[orderColumn]);
                searchParameters.setOrder(orderDir);
            } else {
                searchParameters.setSort(null); // Default sort field
                searchParameters.setOrder(null); // Default sort order
            }
            Page<ProjectDto> projectPage = projectService.search(searchParameters);

            // Create response object
            Map<String, Object> response = new HashMap<>();
            response.put("draw", draw);
            response.put("recordsTotal", projectPage.getTotalElements());
            response.put("recordsFiltered", projectPage.getTotalElements());
            response.put("data", projectPage.getContent());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Search project @return Page<ProjectDto>
    @GetMapping("/projects/search")
    public ResponseEntity<Object> searchProject(@RequestBody ProjectSearchParameters searchParameters){
        try{
            Page<ProjectDto> = projectService.search(searchParameters);
            return ResponseEntity.ok(projectPage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
