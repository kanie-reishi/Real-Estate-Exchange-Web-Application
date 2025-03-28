package devcamp.realestateexchange.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@Controller
public class HomeController {
    // This is the main page
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/realestate/create")
    public String createArticle() {
        return "article-create";
    }

    @GetMapping("/realestate/list")
    public String realestateList() {
        return "article-list";
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
}
