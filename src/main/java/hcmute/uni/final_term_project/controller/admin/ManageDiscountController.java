package hcmute.uni.final_term_project.controller.admin;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import hcmute.uni.final_term_project.entity.Promo;
import hcmute.uni.final_term_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ManageDiscountController {
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
    @GetMapping("/admin/discount")
    public String discount(Model model) {
        List<Promo> promos = promoService.getAllPromos();

        model.addAttribute("promos", promos);
        return "admin/manage-discount";
    }
    @PostMapping("/admin/discount/edit")
    public String editVoucher(
            @RequestParam("code") String code,
            @RequestParam("value") Double value,
            @RequestParam("uses") int uses,
            @RequestParam("limitUsage") int limitUsage,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        // Tìm voucher (promo) theo code
        Promo promo = promoService.getPromoByCode(code);
        if (promo != null) {
            // Cập nhật các trường
            promo.setValue(value);
            promo.setUses(uses);
            promo.setLimitUsage(limitUsage);
            promo.setStartDate(startDate.atStartOfDay()); // Chuyển LocalDate thành LocalDateTime
            promo.setEndDate(endDate.atStartOfDay());

            // Lưu thay đổi
            promoService.saveOrUpdatePromo(promo);
        }
        // Chuyển hướng về trang danh sách vouchers
        return "redirect:/admin/discount";
    }
    @PostMapping("/admin/discount/add")
    public String addVoucher(
            @RequestParam("code") String code,
            @RequestParam("value") Double value,
            @RequestParam("limitUsage") int limitUsage,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            RedirectAttributes redirectAttributes) {

        // Kiểm tra nếu mã voucher đã tồn tại
        if (promoService.getPromoByCode(code) != null) {
            redirectAttributes.addFlashAttribute("error", "Promo code already exists.");
            return "redirect:/admin/discount"; // Trả về trang danh sách vouchers
        }

        // Tạo đối tượng Promo mới
        Promo promo = new Promo();
        promo.setCode(code);
        promo.setValue(value);
        promo.setLimitUsage(limitUsage);
        promo.setStartDate(startDate.atStartOfDay()); // Chuyển LocalDate thành LocalDateTime
        promo.setEndDate(endDate.atStartOfDay());
        promo.setUses(0); // Ban đầu số lần sử dụng là 0

        // Lưu vào cơ sở dữ liệu
        promoService.saveOrUpdatePromo(promo);

        // Thêm thông báo thành công
        redirectAttributes.addFlashAttribute("success", "Promo added successfully.");

        return "redirect:/admin/discount"; // Trả về trang danh sách vouchers
    }
    @PostMapping("/admin/discount/delete")
    public String deleteVoucher(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
        // Tìm promo theo mã code
        Promo promo = promoService.getPromoByCode(code);
        if (promo != null) {
            // Xóa promo
            promoService.deletePromoById(promo.getPromoId());
            redirectAttributes.addFlashAttribute("success", "Voucher deleted successfully.");
            return "redirect:/admin/discount"; // Chuyển hướng về trang danh sách vouchers
        }

        // Nếu không tìm thấy promo
        redirectAttributes.addFlashAttribute("error", "Voucher not found.");
        return "redirect:/admin/discount"; // Chuyển hướng về trang danh sách vouchers
    }

}
