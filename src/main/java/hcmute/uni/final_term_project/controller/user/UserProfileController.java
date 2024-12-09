package hcmute.uni.final_term_project.controller.user;

import hcmute.uni.final_term_project.entity.Document;
import hcmute.uni.final_term_project.service.DocumentService;
import hcmute.uni.final_term_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserProfileController {
    private final UserService userService;
    private final DocumentService documentService;

    @Autowired
    public UserProfileController(UserService userService , DocumentService documentService) {
        this.userService = userService;
        this.documentService = documentService;
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("bio", userService.getCurrentUser().getBio());
        model.addAttribute("email", userService.getCurrentUser().getEmail());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/my-profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam("name") String name,
                                @RequestParam("bio") String bio,
                                @RequestParam("email") String email,
                                @RequestParam("avatar") MultipartFile file,
                                Model model) throws IOException {

        // lưu avatar vào upload folder
        if (!file.isEmpty()){
            // Xóa avatar cũ
            String oldAvartarPathStr = "uploads/";
            oldAvartarPathStr += userService.getCurrentUser().getAvatar();
            Path oldAvartarPath = Paths.get(oldAvartarPathStr);
            try {
                Files.deleteIfExists(oldAvartarPath);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Failed to delete old avartar file.");
            }

            // Lưu avatar mới
            String avatarDir = "uploads/avatars/";
            String avatarFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path avatarPath = Paths.get(avatarDir);
            if (!Files.exists(avatarPath)) {
                Files.createDirectories(avatarPath);
            }
            Path savedDocumentPath = avatarPath.resolve(avatarFileName);
            Files.copy(file.getInputStream(), savedDocumentPath, StandardCopyOption.REPLACE_EXISTING);
            userService.updateProfile(name, bio, email, "avatars/" + avatarFileName);
        }
        else {
            userService.updateProfile(name, bio, email, userService.getCurrentUser().getAvatar());
        }

        return "redirect:/user/profile";
    }

    // endpoint setting page
    @GetMapping("/setting")
    public String showSettingsPage(Model model) {


        // get data from current user
        model.addAttribute("currentUserName", userService.getCurrentUser().getName());
        model.addAttribute("isVIP", userService.getCurrentUser().isVIP());
        model.addAttribute("avatar", userService.getCurrentUser().getAvatar());
        model.addAttribute("followers", userService.getCurrentUser().getFollowers());
        model.addAttribute("bio", userService.getCurrentUser().getBio());
        model.addAttribute("email", userService.getCurrentUser().getEmail());
        model.addAttribute("documentsUploaded", documentService.getDocumentsByOwner(userService.getCurrentUser()).size());

        return "user/setting";
    }

    // endpoint to change password
    @PostMapping("/setting/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New password and confirm password do not match.");
            return "redirect:/user/setting";
        }

        if (!userService.changePassword(oldPassword, newPassword)) {
            model.addAttribute("error", "Old password is incorrect.");
            return "redirect:/user/setting";
        }

        return "redirect:/user/setting";
    }

    // endpoint to delete account
    @PostMapping("/setting/delete-account")
    public String deleteAccount(Model model) {
        userService.deleteAccount();
        return "redirect:/logout";
    }
}
