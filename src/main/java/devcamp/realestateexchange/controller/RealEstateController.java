package devcamp.realestateexchange.controller;

import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import devcamp.realestateexchange.dto.realestate.RealEstateDto;
import devcamp.realestateexchange.entity.authentication.User;
import devcamp.realestateexchange.entity.realestate.RealEstate;
import devcamp.realestateexchange.models.RealEstateSearchParameters;
import devcamp.realestateexchange.security.jwt.JwtUtils;
import devcamp.realestateexchange.security.services.UserDetailsImpl;
import devcamp.realestateexchange.services.realestate.RealEstateService;

@RestController
@CrossOrigin
@RequestMapping
public class RealEstateController {
    @Autowired
    private RealEstateService realEstateService;

    @Autowired
    JwtUtils jwtUtils;

    // REST API for getting real estate list
    @GetMapping("/realestate")
    public ResponseEntity<Object> getRealEstateList(Pageable pageable) {
        try {
            // Check user, only admin can see unapproved real estates
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            if (isAdmin) {
                Page<RealEstateDto> realEstatePage = realEstateService.getAllRealEstateDtos(pageable, 1);
                return ResponseEntity.ok(realEstatePage);
            }
            Page<RealEstateDto> realEstatePage = realEstateService.getAllRealEstateDtos(pageable, 0);
            return ResponseEntity.ok(realEstatePage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST API for getting real estate table
    @GetMapping("/admin/realestate/table")
    public ResponseEntity<Object> getRealEstateTable(
            @RequestParam Map<String, String> allRequestParams) {
        try {
            // Get request parameters
            // draw counter. This is used by DataTables to ensure that the Ajax returns from
            // server-side processing requests are drawn in sequence by DataTables
            Integer draw = Integer.parseInt(allRequestParams.get("draw") != null ? allRequestParams.get("draw") : "0");
            // Paging first record indicator. This is the start point in the current data
            // set (0 index based - i.e. 0 is the first record).
            Integer start = Integer
                    .parseInt(allRequestParams.get("start") != null ? allRequestParams.get("start") : "0");
            // Number of records that the table can display in the current draw. It is
            // expected that the number of records returned will be equal to this number,
            // unless the server has fewer records to return.
            Integer length = Integer
                    .parseInt(allRequestParams.get("length") != null ? allRequestParams.get("length") : "10");
            //
            String orderColumnStr = allRequestParams.get("order[0][column]");
            // Column to which ordering should be applied. This is an index reference to the
            // columns array of information that is also submitted to the server.
            Integer orderColumn = orderColumnStr != null ? Integer.parseInt(allRequestParams.get("order[0][column]"))
                    : null;
            // Ordering direction for this column. It will be asc or desc to indicate
            // ascending ordering or descending ordering, respectively.
            String orderDir = allRequestParams.get("order[0][dir]");
            // Global search value
            String searchTerm = allRequestParams.get("search[value]");
            // Search regex
            Boolean searchRegex = Boolean.parseBoolean(allRequestParams.get("search[regex]") != null
                    ? allRequestParams.get("search[regex]")
                    : "false");
            // Check if what column is orderable
            String orderableKey = "columns[" + orderColumn + "][orderable]";
            // Check if the column is orderable
            String orderable = allRequestParams.get(orderableKey);

            // Define sort column
            String[] columns = new String[] { "", "realEstateCode", "addressDetail.province.id",
                    "addressDetail.district.id", "addressDetail.ward.id", "addressDetail.street.id",
                    "address", "title", "description", "type", "request", "customer.id", "price", "createdAt",
                    "acreage", "priceTime", "images" };
            // Define search parameters
            RealEstateSearchParameters realEstateSearchParameters = new RealEstateSearchParameters();
            realEstateSearchParameters.setSearchText(searchTerm);
            realEstateSearchParameters.setSize(length);
            realEstateSearchParameters.setFrom(start);
            // Check if the column is orderable
            if (orderColumn != null && orderColumn >= 0 && orderColumn < columns.length && orderable.equals("true")
                    && orderColumnStr != null && orderDir != null) {
                realEstateSearchParameters.setSort(columns[orderColumn]);
                realEstateSearchParameters.setOrder(orderDir);
            } else {
                realEstateSearchParameters.setSort(null); // Default sort field
                realEstateSearchParameters.setOrder(null); // Default sort order
            }
            Page<RealEstateDto> realEstatePage = realEstateService.search(realEstateSearchParameters);

            // Create response object
            Map<String, Object> response = new HashMap<>();
            response.put("draw", draw);
            response.put("recordsTotal", realEstatePage.getTotalElements());
            response.put("recordsFiltered", realEstatePage.getTotalElements());
            response.put("data", realEstatePage.getContent());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST API for searching real estates
    @PostMapping("/realestate/search")
    public ResponseEntity<Object> searchRealEstates(
            @RequestBody RealEstateSearchParameters realEstateSearchParameters) {
        try {
            Page<RealEstateDto> realEstateDtos = realEstateService.search(realEstateSearchParameters);
            return ResponseEntity.ok(realEstateDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST API for getting real estate by id
    @GetMapping("/realestate/{id}/detail")
    public ResponseEntity<Object> getRealEstateById(@PathVariable Integer id) {
        try {
            // Check user, only admin can see unapproved real estates
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // Get real estate by id
            RealEstateDto realEstate = realEstateService.getRealEstateById(id);
            // Check if real estate is not found
            if (realEstate == null) {
                return ResponseEntity.badRequest().body("Real estate not found");
            }
            if (authentication != null) {
                boolean isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
                // Check if user is admin, if true return real estate
                if (isAdmin == true) {
                    return ResponseEntity.ok(realEstate);
                } else {
                    // Check if user is not admin, check if real estate is not approved
                    if (realEstate.getVerify() == null || realEstate.getVerify() == 0) {
                        return ResponseEntity.badRequest().body("Real estate is not approved");
                    }
                }
            } else {
                // Check if user is not admin, check if real estate is not approved
                if (realEstate.getVerify() == null || realEstate.getVerify() == 0) {
                    return ResponseEntity.badRequest().body("Real estate is not approved");
                }
            }
            return ResponseEntity.ok(realEstate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST API for creating real estate
    @PostMapping("/realestate")
    public ResponseEntity<Object> createRealEstate(@RequestBody(required = false) RealEstateDto realEstateDto) {
        try {
            RealEstateDto realEstate = realEstateService.saveRealEstate(realEstateDto);
            return ResponseEntity.ok(realEstate);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    // REST API for updating real estate
    @PutMapping("/admin/realestate/update")
    public ResponseEntity<Object> updateRealEstate(@RequestBody RealEstateDto realEstateDto) {
        try {
            realEstateService.saveRealEstate(realEstateDto);
            return ResponseEntity.ok("Real estate updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST API for approving real estate
    @PutMapping("/admin/realestate/verify/{id}")
    public ResponseEntity<Object> verifyRealEstate(@PathVariable Integer id) {
        try {
            realEstateService.verifyRealEstateById(id);
            return ResponseEntity.ok("Real estate approved successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST API for restore real estate
    @PutMapping("/admin/realestate/restore/{id}")
    public ResponseEntity<Object> restoreRealEstate(@PathVariable Integer id) {
        try {
            realEstateService.restoreRealEstateById(id);
            return ResponseEntity.ok("Real estate restored successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST API for deleting real estate (hard delete)
    @DeleteMapping("/admin/realestate/{id}/hard-delete")
    public ResponseEntity<Object> deleteRealEstate(@PathVariable Integer id) {
        try {
            realEstateService.deleteRealEstateById(id);
            return ResponseEntity.ok("Real estate deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST API for deleting real estate (soft delete)
    @DeleteMapping("/admin/realestate/{id}")
    public ResponseEntity<Object> softDeleteRealEstate(@PathVariable Integer id) {
        try {
            realEstateService.softDeleteRealEstateById(id);
            return ResponseEntity.ok("Real estate soft deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST API for indexing all real estates
    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/realestate/indexall")
    public ResponseEntity<Object> indexAllRealEstates() {
        try {
            realEstateService.indexAllRealEstates();
            return ResponseEntity.ok("Index All Real Estate successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST API for getting real estate detail by id
    @GetMapping("/realestate/{id}")
    public ModelAndView getRealEstateDetail(@PathVariable("id") Long id, Model model) {
        // Add the ID to the model to use it in the view
        model.addAttribute("id", id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("article-detail");
        return modelAndView;
    }

    // REST API for indexing test real estates
    @GetMapping("/realestate/indextest")
    public ResponseEntity<Object> indexTestRealEstate() {
        try {
            realEstateService.indexTestRealEstate();
            return ResponseEntity.ok("Index Test Real Estate successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // REST API for returning real estate edit form
    @GetMapping("/admin/realestate/form")
    public ModelAndView realestateForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin-realestate-form");
        return modelAndView;
    }

    // REST API for returning real estate preview
    @GetMapping("/admin/realestate/preview/{id}")
    public ModelAndView realestatePreview(@PathVariable("id") Long id, Model model) {
        // Add the ID to the model to use it in the view
        model.addAttribute("id", id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin-realestate-preview");
        return modelAndView;
    }

    // REST API for returning real estate form
    @GetMapping("/admin/realestate/form/{id}")
    public ModelAndView realestateForm(@PathVariable("id") Long id, Model model) {
        // Add the ID to the model to use it in the view
        model.addAttribute("id", id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin-realestate-form");
        return modelAndView;
    }

    // REST API for returning real estate table
    @GetMapping("/admin/realestates")
    public ModelAndView realestateTable() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("realestate-table");
        return modelAndView;
    }

    // REST API for returning real estate detail
    @GetMapping("/admin/realestate/{id}/detail")
    public ModelAndView realestateDetail(@PathVariable("id") Long id, Model model) {
        // Add the ID to the model to use it in the view
        model.addAttribute("id", id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("realestate-detail");
        return modelAndView;
    }

    // REST API for returning verify real estate page
    @GetMapping("/admin/realestate/verify")
    public ModelAndView realestateVerify() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("realestate-verify");
        return modelAndView;
    }

    @GetMapping("/customers/{customerId}/realestate")
    public ResponseEntity<Page<RealEstateDto>> getArticlesByCustomer(
            @PathVariable Integer customerId,
            @RequestHeader("Authorization") String authorizationHeader,
            Pageable pageable) {
        // Lấy thông tin từ JWT
        String token = authorizationHeader.substring(7);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        // Kiểm tra xem người dùng có phải là chủ sở hữu tin đăng không
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).build(); // Forbidden
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Integer userId = userDetails.getId();
        if (userId != customerId) {
            return ResponseEntity.status(403).build(); // Forbidden
        }

        // Lấy danh sách tin đăng của khách hàng
        Page<RealEstateDto> articles = realEstateService.getRealEstatesByCustomerId(customerId, pageable);
        if (articles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(articles);
    }
}
