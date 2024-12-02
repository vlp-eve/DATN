package com.poly.datn.Controller.Admin;

import com.poly.datn.Entity.User.Account;
import com.poly.datn.Service.AccountService;
import com.poly.datn.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class Admin {
    @Autowired
    RoleService roleService;

    @Autowired
    AccountService accountService;

    @GetMapping("/phanquyen")
    public String viewForm(Model model) {
        model.addAttribute("listRole", roleService.findAll());
        model.addAttribute("account", new Account());
        return"assets/form/phanquyen";
    }
    @GetMapping("/list")
    public String viewList(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @Param("keyword") String keyword) {
        Page<Account> list = this.accountService.findAll(pageNo);
        if(keyword != null) {
            list = accountService.searchAccountKeyword(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        }
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listAccount", list);
        return"assets/form/listphanquyen";
    }

    @GetMapping("/phanquyen/{id}")
    public String edit (@PathVariable("id") Long id, Model model) {
        model.addAttribute("listRole", roleService.findAll());
        Optional<Account> account = accountService.findById(id);
        if(account.isPresent()) {
            model.addAttribute("account", account.get());
        }else {
            model.addAttribute("account", new Account());
        }
        return"assets/form/phanquyen";
    }
    @PostMapping("/update")
    public String update(Model model, @ModelAttribute("account") Account account) {
        accountService.save(account);
        model.addAttribute("listRole", roleService.findAll());
        model.addAttribute("account", new Account());
        return "assets/form/phanquyen";

    }
}
