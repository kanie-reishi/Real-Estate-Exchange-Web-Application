package devcamp.realestateexchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/admin/realestate/{id}/detail")
    public String realestateDetail(@PathVariable("id") Long id, Model model) {
        // Add the ID to the model to use it in the view
        model.addAttribute("id", id);
        return "realestate-detail";
    } 
    @GetMapping("/admin/project-table")
    public String projectTable() {
        return "project-table";
    }
}
