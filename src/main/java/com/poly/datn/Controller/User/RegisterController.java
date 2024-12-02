package com.poly.datn.Controller.User;


import com.poly.datn.Entity.User.User;
import com.poly.datn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class RegisterController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/register/form")
	public String showFormRegister(Model model) {
		model.addAttribute("user", new User());
		return "assetss/form/login/register";
	}
	@PostMapping("/register/submit")
	public String submitRegister(@Validated @ModelAttribute("user") User user, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return "assetss/form/login/register";
		}
		// Kiểm tra trùng lặp email
	    if (userService.existsByEmail(user.getEmail())) {
	        model.addAttribute("emailError", "Email đã tồn tại!");
	        return "assetss/form/login/register";  // Trả về trang đăng ký với thông báo lỗi
	    }

	    // Kiểm tra trùng lặp số điện thoại
	    if (userService.existsByPhone(user.getPhone())) {
	        model.addAttribute("phoneError", "Số điện thoại đã tồn tại!");
	        return "assetss/form/login/register";
	    }

	    // Kiểm tra trùng lặp username
	    if (userService.existsByUsername(user.getUsername())) {
	        model.addAttribute("usernameError", "Username đã tồn tại!");
	        return "assetss/form/login/register";
	    }

		
		userService.registerUser(user);
		return "redirect:/security/login/form";
	}
}
