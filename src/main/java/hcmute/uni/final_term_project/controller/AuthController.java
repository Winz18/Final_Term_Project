package hcmute.uni.final_term_project.controller;

import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    // Hiển thị trang login
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("error", null); // Để thông báo lỗi (nếu có)
        System.out.print("CCCC");
        return "login"; // templates/login.html
    }

    // Xử lý đăng nhập
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("email") String email,
                              @ModelAttribute("password") String password,
                              Model model) {
        try {
            // Xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Đăng nhập thành công
            User user = userService.getUserByEmail(email);
            System.out.print(email);
            userService.updateLastLogin(user.getUserId()); // Cập nhật thời gian đăng nhập cuối cùng
            return "redirect:/user/home";

        } catch (AuthenticationException e) {
            // Đăng nhập thất bại
            model.addAttribute("error", "Thông tin đăng nhập không hợp lệ hoặc tài khoản bị vô hiệu hóa.");
            return "login";
        }
    }

    // Hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User()); // Chuẩn bị đối tượng rỗng để binding form
        return "register"; // templates/register.html
    }

    // Xử lý đăng ký tài khoản
    @PostMapping("/register")
    public String handleRegister(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("numberphone") String numberphone,
            @RequestParam("email") String email,
            @RequestParam("birthdate") String birthdate,
            @RequestParam("status") String status,
            Model model
    ) {
        try {
            User user = new User();
            // Kiểm tra email đã tồn tại
            user.setName(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));

            // Thiết lập các giá trị mặc định
            user.setActive(true);
            user.setAdmin(false);
            user.setVIP(false);

            // Lưu người dùng vào cơ sở dữ liệu
            userService.saveOrUpdateUser(user);

            model.addAttribute("message", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi đăng ký. Vui lòng thử lại.");
            return "register";
        }
    }

    // Xử lý logout
    @GetMapping("/logout")
    public String handleLogout() {
        return "redirect:/login?logout=true"; // Đã được cấu hình trong SecurityConfig
    }
}
