package hcmute.uni.final_term_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

    // Hiển thị trang chủ cho guest
    @GetMapping("/")
    public String showIndexPage() {
        return "index"; // file templates/index.html
    }

    // Hiển thị trang login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // file templates/login.html
    }

    // Hiển thị trang register
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // file templates/register.html
    }

    // Hiển thị trang about us
    @GetMapping("/about")
    public String showAboutUsPage() {
        return "about_us"; // file templates/about_us.html
    }
}
