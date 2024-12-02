package com.poly.datn.Controller.Admin;

import com.poly.datn.Entity.User.User;
import com.poly.datn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class UserAdminController {
    @Autowired
    UserService userService;

    @GetMapping("/user/list")
    public String viewListAdm(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                              @Param("keyword") String keyword) {

        Page<User> list = this.userService.findAll(pageNo);

        if (keyword != null) {
            list = userService.searchUser(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("userList", list);
        model.addAttribute("css","/assets/css/listUser.css");
        return "assets/form/listUser";
    }
}
