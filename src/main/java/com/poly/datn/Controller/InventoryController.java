package com.poly.datn.Controller;

import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Repository.Product1Repository;
import com.poly.datn.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private Product1Repository product1Repository;

    @GetMapping("/add")
    public String showAddInventoryForm(Model model) {
        model.addAttribute("inventory", new Inventory());
        model.addAttribute("products", product1Repository.findAll());  // Hiển thị danh sách sản phẩm có sẵn
        return "add-inventory";
    }

    @PostMapping("/save")
    public String saveInventory(@ModelAttribute("inventory") Inventory inventory, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("products", product1Repository.findAll());
            return "add-inventory";
        }

        try {
            inventoryService.saveInventory(inventory);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("products", product1Repository.findAll());
            return "add-inventory";
        }

        return "redirect:/inventory/list";
    }

    @GetMapping("/list")
    public String listInventories(Model model) {
        model.addAttribute("inventories", inventoryService.getAllInventories());
        return "list-inventories";
    }
}
