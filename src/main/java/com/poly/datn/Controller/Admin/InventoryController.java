package com.poly.datn.Controller.Admin;

import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product;
import com.poly.datn.Service.InventoryService;
import com.poly.datn.Service.Product1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/inventorys")
public class InventoryController {

    @Autowired
    private Product1Service product1Service;

    @Autowired
    private InventoryService inventoryService;


    @GetMapping("/select-product")
    public String selectProduct(Model model) {
        List<Product> products = product1Service.getNonDeletedProducts();

        Map<Long, Long> inventoryCounts = new HashMap<>();

        for (Product product : products) {
            Long count = inventoryService.getInventoryCount(product.getId());
            inventoryCounts.put(product.getId(), count);
        }
        model.addAttribute("inventoryCounts",inventoryCounts);
        model.addAttribute("products", products);
        model.addAttribute("css", "/assets/css/selectInventory.css");
        return "assets/form/selectInventory";
    }

    @GetMapping("{productId}")
    public String viewInventory(@PathVariable("productId") Long productId, Model model) {
        List<Inventory> inventories = inventoryService.getInventoryByProductId(productId);
        model.addAttribute("inventories", inventories);
        model.addAttribute("css", "/assets/css/qlInventory.css");
        return "assets/form/listInventory";
    }
}
