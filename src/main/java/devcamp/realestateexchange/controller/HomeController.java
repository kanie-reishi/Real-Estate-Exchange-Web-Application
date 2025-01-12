package devcamp.realestateexchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@Controller
public class HomeController {
    // This is the main page
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/realestate/list")
    public String getArticles() {
        return "article-list";
    }

    @GetMapping("/realestate/create")
    public String createArticle() {
        return "article-create";
    }

    @GetMapping("/admin")
    public String adminIndex() {
        return "admin-index";
    }

    // Trang login cho admin
    @GetMapping("/login/admin")
    public String adminLogin(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "Bạn cần đăng nhập với tài khoản admin để truy cập.");
        }
        return "admin-login"; // Tên file HTML cho trang login admin
    }

    // Trang login cho user
    @GetMapping("/login/user")
    public String userLogin(Model model, @RequestParam(value = "error", required = false) String error) {
        if (error != null) {
            model.addAttribute("errorMessage", "Bạn cần đăng nhập với tài khoản người dùng để truy cập.");
        }
        return "user-login"; // Tên file HTML cho trang login user
    }

    @GetMapping("/admin/realestate/form")
    public String realestateForm() {
        return "admin-realestate-form";
    }

    @GetMapping("/admin/realestate/preview/{id}")
    public String realestatePreview(@PathVariable("id") Long id, Model model) {
        // Add the ID to the model to use it in the view
        model.addAttribute("id", id);
        model.addAttribute("mode", "preview");
        return "article-detail";
    }

    @GetMapping("/admin/realestate/form/{id}")
    public String realestateForm(@PathVariable("id") Long id, Model model) {
        // Add the ID to the model to use it in the view
        model.addAttribute("id", id);
        return "admin-realestate-form";
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
