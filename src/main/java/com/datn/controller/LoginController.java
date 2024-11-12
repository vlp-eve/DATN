package com.datn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	@RequestMapping("/security/login/form")
	public String loginForm(Model model) {
		model.addAttribute("message", "Vui lòng đăng nhập");
		return "account/login";
	}
	@RequestMapping("/security/login/success")
	public String loginSuccess(Model model) {
		model.addAttribute("message", "Đăng nhập thành công");
		return "account/login";
	}
	@RequestMapping("/security/login/error")
	public String loginErorr(Model model) {
		model.addAttribute("message", "Sai thông tin đăng nhập!");
		return "account/login";
	}
	@RequestMapping("/security/unauthorized")
	public String loginUnauthoried(Model model) {
		model.addAttribute("message", "Không có quyền truy xuất");
		return "account/unauthorized";
	}
	@RequestMapping("/security/logoff/success")
	public String Logoff(Model model) {
		model.addAttribute("message", "Đăng xuất thành công");
		return "account/login";
	}
	
	@RequestMapping("/admin/hehe")
	public String admin() {
		return"account/admin";
	}
}
