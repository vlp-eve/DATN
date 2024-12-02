package com.poly.datn.Controller.User;

import com.poly.datn.Entity.DTO.StoreDTO;
import com.poly.datn.Entity.Product.Discount;
import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product;
import com.poly.datn.Entity.User.User;
import com.poly.datn.Service.CategoryService;
import com.poly.datn.Service.Impl.Product.ProductServiceImpl;
import com.poly.datn.Service.StoreService;
import com.poly.datn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
public class IndexController {
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;

    @Autowired
    private StoreService storeService;
    @GetMapping()
    public String viewIndex(Model model) {
        List<Object[]> results = storeService.getProductsWithUpcomingExpiry();
        List<StoreDTO> storeResponses = new ArrayList<>();

        for (Object[] result : results) {
            Long storeId = (Long) result[0];
            Discount discount = (Discount) result[1];
            Product product = (Product) result[2];
            Inventory inventory = (Inventory) result[3];

            storeResponses.add(new StoreDTO(storeId, discount, product, inventory));
        }

        // Lấy ngẫu nhiên 8 sản phẩm từ danh sách
        Collections.shuffle(storeResponses); // Trộn ngẫu nhiên danh sách
        List<StoreDTO> randomStores = storeResponses.stream()
                .limit(8) // Giới hạn 8 sản phẩm
                .collect(Collectors.toList());

        // Thêm dữ liệu vào model để truyền đến giao diện
        model.addAttribute("stores", randomStores);
        model.addAttribute("css","/assetss/css/mainIndex.css");
        return "assetss/layout/mainIndex";
    }

    @GetMapping("/profile/form/{id}")
    public String viewProfile(@PathVariable("id") Long id, Model model) {
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()) {
            model.addAttribute("puser", user.get());
        }else {
            model.addAttribute("puser", new User());
        }
        return"account/profile";
    }
    @GetMapping("/profile/form")
    public String viewForm(Model model) {
        model.addAttribute("puser", new User());
        return"account/profile";
    }
    @PostMapping("/profile/update")
    public String updateProfile(@Validated @ModelAttribute("puser") User puser , BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "account/profile";
        }
        Optional<User> currentUserOpt = userService.getUserById(puser.getId());
        if (!currentUserOpt.isPresent()) {
            model.addAttribute("error", "Người dùng không tồn tại!");
            return "account/profile";
        }
        User currentUser = currentUserOpt.get();
        // Kiểm tra trùng lặp email
        if (!puser.getEmail().equals(currentUser.getEmail()) && userService.existsByEmail(puser.getEmail())) {
            model.addAttribute("emailError", "Email đã tồn tại!");
            return "account/profile";  // Trả về trang đăng ký với thông báo lỗi
        }

        // Kiểm tra trùng lặp số điện thoại
        if (!puser.getPhone().equals(currentUser.getPhone()) && userService.existsByPhone(puser.getPhone())) {
            model.addAttribute("phoneError", "Số điện thoại đã tồn tại!");
            return "account/profile";
        }


        LocalDate today = LocalDate.now();
        puser.setCreateAt(today);
        userService.save(puser);
        return "redirect:/index";
    }
}
