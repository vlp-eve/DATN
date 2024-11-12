package com.datn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.datn.entity.User1;
import com.datn.service.UserService;

@Controller
public class RegisterController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/register/form")
	public String showFormRegister(Model model) {
		model.addAttribute("user", new User1());
		return "account/register";
	}
	@PostMapping("/register/submit")
	public String submitRegister(@ModelAttribute("user") User1 user) {
		userService.registerUser(user);
		return "redirect:/security/login/form";
	}
}
