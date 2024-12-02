package com.poly.datn.Controller.Admin;

import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product;
import com.poly.datn.Service.InventoryService;
import com.poly.datn.Service.Product1Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/inventorys")
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

    @GetMapping("/add")
    public String addInventory(@RequestParam(value = "productId", required = false) Long productId, Model model) {
        Inventory inventory = new Inventory();
        if (productId != null) {
            Product product = product1Service.getById(productId);
            if (product != null) {
                inventory.setProduct(product);
            }
        }
//        model.addAttribute("css", )
        model.addAttribute("inventory", inventory);
        return "assets/form/editInventory";
    }
    @PostMapping("/save")
    public String saveInventory(@ModelAttribute("inventory") Inventory inventory, Model model) {
        try {
            inventoryService.saveInventory(inventory);


            model.addAttribute("message", "Lưu lô hàng thành công!");


            return "assets/form/editInventory";
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi lưu lô hàng: " + e.getMessage());
            return "assets/form/editInventory";
        }
    }

    @GetMapping("edit/{id}")
    public String showEditInventoryForm(@PathVariable("id") Long id, Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);

        if (inventory == null) {
            model.addAttribute("error", "Không tìm thấy Lô Hàng với ID: " + id);
            return "redirect:/admin/inventory/list"; // Quay lại danh sách nếu không tìm thấy
        }



        model.addAttribute("inventory", inventory);

        List<Product> products = product1Service.getAllProducts();
        model.addAttribute("products", products);

        model.addAttribute("css", "/assets/css/editInventory.css");

        return "assets/form/editInventory";
    }
    @PostMapping("/edit/{id}")
    public String updateInventory(@PathVariable("id") Long id,
                                  @Valid @ModelAttribute Inventory inventory,
                                  BindingResult result,
                                  Model model) {

        // Nếu có lỗi trong form
        if (result.hasErrors()) {
            // Trả về danh sách sản phẩm để hiển thị trong form nếu cần
            List<Product> products = product1Service.getAllProducts();
            model.addAttribute("products", products);

            if (inventory.getReceivedDate() == null && id != null) {
                Inventory existingInventory = inventoryService.getInventoryById(id);
                inventory.setReceivedDate(existingInventory.getReceivedDate());
            }

            if (inventory.getExpiryDate() == null && id != null) {
                Inventory existingInventory = inventoryService.getInventoryById(id);
                inventory.setExpiryDate(existingInventory.getExpiryDate());
            }

            model.addAttribute("inventory", inventory);
            model.addAttribute("css", "/assets/css/editInventory.css");

            return "assets/form/editInventory";
        }

        // Nếu không có lỗi, tiến hành cập nhật thông tin kho hàng
        inventoryService.updateInventory(inventory);
        model.addAttribute("message", "Sửa thành công lô hàng với mã lô hàng: "+inventory.getId());
        return "assets/form/editInventory";
    }




}
