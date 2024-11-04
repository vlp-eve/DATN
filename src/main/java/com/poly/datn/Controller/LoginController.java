package com.poly.datn.Controller;

import com.poly.datn.Entity.Product.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginForm(Model model) {
        return "login/login";
    }
    @GetMapping("/register")
    public String registerFrom(Model model){
        return "login/register";
    }
}
