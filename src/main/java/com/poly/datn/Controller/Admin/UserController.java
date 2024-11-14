package com.poly.datn.Controller.Admin;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("css","/assets/css/listUser.css");
        return "assets/form/listUser";
    }

}
