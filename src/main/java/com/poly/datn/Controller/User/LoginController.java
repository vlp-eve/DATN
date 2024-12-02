package com.poly.datn.Controller.User;

import com.poly.datn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@RequestMapping("/security/login/form")
	public String loginForm(Model model) {
		model.addAttribute("message", "Vui lòng đăng nhập");
		return "assetss/form/login/login";
	}

	@RequestMapping("/security/login/success")
	public String loginSuccess(Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("message", "Đăng nhập thành công");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		redirectAttributes.addFlashAttribute("username", authentication.getName());

		return "redirect:/";
	}

	@RequestMapping("/security/login/error")
	public String loginErorr(Model model) {
		model.addAttribute("message", "Sai thông tin đăng nhập!");
		return "assetss/form/login/login";
	}

	@RequestMapping("/security/unauthorized")
	public String loginUnauthoried(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất");
		return "error";
	}

	@RequestMapping("/security/logoff/success")
	public String Logoff(Model model) {
		model.addAttribute("message", "Đăng xuất thành công");
		return "redirect:/security/login/form";
	}

	@RequestMapping("/admin/thongke")
	public String admin() {
		return "admin/ThongKe";
	}
}
