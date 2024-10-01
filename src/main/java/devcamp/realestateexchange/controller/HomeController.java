package devcamp.realestateexchange.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/admin")
    public String adminIndex() {
        return "admin-index";
    }
    
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "admin-login";
    }
    
    @GetMapping("/admin/realestate-table")
    public String realestateTable() {
        return "realestate-table";
    }
    @GetMapping("/admin/realestate-detail")
    public String realestateDetail() {
        return "realestate-detail";
    }
}
