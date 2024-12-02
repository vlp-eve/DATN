package com.poly.datn.Controller.Admin;


import com.poly.datn.Entity.User.User;
import com.poly.datn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/staff/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public String viewListUser(Model model){
        List<User> users = userService.findUserWithNonDelete();
        model.addAttribute("users", users);
        model.addAttribute("css","/assets/css/listUser.css");
        return "assets/form/listUser";
    }

    @GetMapping("/form")
    public String viewForm(Model model) {
        model.addAttribute("auser", new User());
        return "assets/form/editUser";
    }

    @PostMapping("/submit")
    public String submitForm(@Validated @ModelAttribute("auser") User auser, BindingResult result, Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            LocalDate date = LocalDate.now();
            auser.setCreateAt(date);
            if (result.hasErrors()) {
                return "assets/form/editUser";
            }
            Optional<User> currentUserOpt = userService.getUserById(auser.getId());
            if (!currentUserOpt.isPresent()) {
                model.addAttribute("error", "Người dùng không tồn tại!");
                return "assets/form/editUser";
            }
            User currentUser = currentUserOpt.get();
            // Kiểm tra trùng lặp email
            if (!auser.getEmail().equals(currentUser.getEmail()) && userService.existsByEmail(auser.getEmail())) {
                model.addAttribute("emailError", "Email đã tồn tại!");
                return "assets/form/editUser";  // Trả về trang đăng ký với thông báo lỗi
            }

            // Kiểm tra trùng lặp số điện thoại
            if (!auser.getPhone().equals(currentUser.getPhone()) && userService.existsByPhone(auser.getPhone())) {
                model.addAttribute("phoneError", "Số điện thoại đã tồn tại!");
                return "assets/form/editUser";
            }

            // Kiểm tra trùng lặp username
            if (!auser.getUsername().equals(currentUser.getUsername()) && userService.existsByUsername(auser.getUsername())) {
                model.addAttribute("usernameError", "Username đã tồn tại!");
                return "assets/form/editUser";
            }
            userService.save(auser);
            redirectAttributes.addFlashAttribute("successMessage", "Sửa thông tin người dùng thành công.");
            return "redirect:/staff/user/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sửa thông tin người dùng thất bại. Vui lòng thử lại.");
            return "redirect:/staff/user/form";
        }
    }

    @GetMapping("/list")
    public String viewList(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                           @Param("keyword") String keyword) {
        Page<User> list = this.userService.findAllUserCust(pageNo);
        if (keyword != null) {
            list = userService.searchUserCust(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("userCust", list);
        model.addAttribute("css","/assets/css/listUser.css");
        return "assets/form/listUserRoleCust";
    }


    @GetMapping("/form/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()) {
            model.addAttribute("auser", user.get());
        }else {
            model.addAttribute("auser", new User());
        }
        return "assets/form/editUser";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.softDeleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa người dùng thành công.");
            return "redirect:/staff/user/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Xóa người dùng thất bại.");
            return "redirect:/staff/user/form";
        }
    }

}
