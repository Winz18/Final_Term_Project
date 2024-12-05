package hcmute.uni.final_term_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class test {
    @Controller
    public static class MainController {

        @GetMapping("/test_UI")
        public String home(Model model) {
            model.addAttribute("userName", "Nguyen Thang Loi");  // Truyền dữ liệu cho giao diện
            return "user/view-doc";  // Trả về giao diện
        }
    }

}
