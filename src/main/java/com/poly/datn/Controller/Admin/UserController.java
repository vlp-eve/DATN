package com.poly.datn.Controller.Admin;


import com.poly.datn.Entity.User.User;
import com.poly.datn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping()
    public String viewListUser(Model model){
        List<User> users = userService.findUserWithNonDelete();
        model.addAttribute("users", users);
        model.addAttribute("css","/assets/css/listUser.css");
        return "assets/form/listUser";
    }
}
