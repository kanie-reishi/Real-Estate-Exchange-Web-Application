package devcamp.realestateexchange.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ErrorController {
    // 403
    @RequestMapping("/accessDenied")
    public String accessDenied() {
        return "403";
    }
}
