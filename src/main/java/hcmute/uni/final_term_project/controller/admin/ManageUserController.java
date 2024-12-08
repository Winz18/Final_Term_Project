package hcmute.uni.final_term_project.controller.admin;

import hcmute.uni.final_term_project.entity.Download;
import hcmute.uni.final_term_project.entity.Follower;
import hcmute.uni.final_term_project.entity.Likes;
import hcmute.uni.final_term_project.entity.User;
import hcmute.uni.final_term_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ManageUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DownloadService downloadService;
    @Autowired
    private PromoService promoService;
    @Autowired
    private FollowerService followerService;
    @Autowired
    private LikesService likesService;
    @GetMapping("/admin/users")
    public String ManageUser(Model model) {
        List<User> users = userService.getAllUsers();
        List<Download> downloads = downloadService.getAllDownloads();
        List<Likes> likes = likesService.getAllLikes();
        List<Follower> followers = followerService.getAllFollowers();
//        download list
        Map<Long, Long> downloadCountByUserId = downloads.stream()
                .collect(Collectors.groupingBy(download -> download.getUser().getUserId(), Collectors.counting()));
        List<Long> listDownload = users.stream()
                .map(user -> downloadCountByUserId.getOrDefault(user.getUserId(), 0L))
                .collect(Collectors.toList());
//        like list
        Map<Long, Long> likesCountByUserId = likes.stream()
                .collect(Collectors.groupingBy(like -> like.getUser().getUserId(), Collectors.counting()));
        List<Long> listLikes = users.stream()
                .map(user -> likesCountByUserId.getOrDefault(user.getUserId(), 0L))
                .collect(Collectors.toList());
//        follow list
        Map<Long, Long> followersCountByUserId = followers.stream()
                .collect(Collectors.groupingBy(follower -> follower.getFollowed().getUserId(), Collectors.counting()));

        List<Long> listFollowers = users.stream()
                .map(user -> followersCountByUserId.getOrDefault(user.getUserId(), 0L))
                .collect(Collectors.toList());

        System.out.println(listDownload);
        List<Map<String, Object>> userData = users.stream()
                .map(user -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("user", user);
                    data.put("downloads", downloadCountByUserId.getOrDefault(user.getUserId(), 0L));
                    data.put("likes", likesCountByUserId.getOrDefault(user.getUserId(), 0L));
                    data.put("followers", followersCountByUserId.getOrDefault(user.getUserId(), 0L));
                    return data;
                })
                .collect(Collectors.toList());

        model.addAttribute("userData", userData);
        return "admin/manage-user"; // file templates/index.html
    }
@PostMapping("/admin/users/edit")
public String EditUser(
        @RequestParam("userName") String name,
        @RequestParam("userEmail") String email,
        @RequestParam(value = "userPassword", required = false) String password,
        @RequestParam(value = "userIsAdmin", required = false) boolean isAdmin,
        @RequestParam(value = "userIsVIP", required = false) boolean isVIP
) {
    User user1 = userService.getUserByEmail(email);
    user1.setName(name);
    user1.setEmail(email);
    user1.setPassword(password);
    user1.setAdmin(isAdmin);
    user1.setVIP(isVIP);
    userService.saveOrUpdateUser(user1);
    return "redirect:/admin/users";
}
@PostMapping("/admin/users/add")
public String AddUser(
        @RequestParam("userName") String name,
        @RequestParam("userEmail") String email,
        @RequestParam(value = "userPassword", required = false) String password,
        @RequestParam(value = "userIsAdmin", required = false) boolean isAdmin,
        @RequestParam(value = "userIsVIP", required = false) boolean isVIP
) {
    User user1 = new User();
    user1.setName(name);
    user1.setEmail(email);
    user1.setPassword(password);
    user1.setAdmin(isAdmin);
    user1.setVIP(isVIP);
    userService.saveOrUpdateUser(user1);
    return "redirect:/admin/users";
}
@PostMapping("/admin/users/delete")
public String DeleteUser(
        @RequestParam("userId") Long id
) {
    userService.deleteUserById(id);
    return "redirect:/admin/users";
}
}
