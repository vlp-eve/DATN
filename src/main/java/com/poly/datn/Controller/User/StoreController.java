package com.poly.datn.Controller.User;


import com.poly.datn.Entity.DTO.StoreDTO;
import com.poly.datn.Entity.Product.Discount;
import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product;
import com.poly.datn.Entity.Product.Store;
import com.poly.datn.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public String getStoresWithValidInventory(Model model) {
        // Gọi service để lấy dữ liệu các sản phẩm có sắp hết hạn
        List<Object[]> results = storeService.getProductsWithUpcomingExpiry();
        List<StoreDTO> storeResponses = new ArrayList<>();

        for (Object[] result : results) {
            Long storeId = (Long) result[0];
            Discount discount = (Discount) result[1];
            Product product = (Product) result[2];
            Inventory inventory = (Inventory) result[3];

            storeResponses.add(new StoreDTO(storeId, discount, product, inventory));
        }

        // Thêm dữ liệu vào model để truyền đến giao diện
        model.addAttribute("stores", storeResponses);
        model.addAttribute("css", "/assetss/css/store.css");
        // Nếu có thông tin giỏ hàng từ flash attribute thì hiển thị
        if (model.containsAttribute("cartDetailList")) {
            model.addAttribute("cartDetailList", model.asMap().get("cartDetailList"));
            model.addAttribute("cart", model.asMap().get("cart"));
            model.addAttribute("message", model.asMap().get("message"));
        }

        return "assetss/form/store";
    }
    @GetMapping("/category/{categoryId}")
    public String getStoresWithValidInventoryAndCategory(@PathVariable Long categoryId, Model model) {
        // Gọi service để lấy dữ liệu các sản phẩm có sắp hết hạn và thuộc category
        List<Object[]> results = storeService.getProductsWithUpcomingExpiryAndCategory(categoryId);
        List<StoreDTO> storeResponses = new ArrayList<>();

        for (Object[] result : results) {
            Long storeId = (Long) result[0];
            Discount discount = (Discount) result[1];
            Product product = (Product) result[2];
            Inventory inventory = (Inventory) result[3];

            storeResponses.add(new StoreDTO(storeId, discount, product, inventory));
        }

        // Thêm dữ liệu vào model để truyền đến giao diện
        model.addAttribute("stores", storeResponses);
        model.addAttribute("css", "/assetss/css/store.css");

        // Nếu có thông tin giỏ hàng từ flash attribute thì hiển thị
        if (model.containsAttribute("cartDetailList")) {
            model.addAttribute("cartDetailList", model.asMap().get("cartDetailList"));
            model.addAttribute("cart", model.asMap().get("cart"));
            model.addAttribute("message", model.asMap().get("message"));
        }

        return "assetss/form/store";
    }

    @GetMapping("/{id}")
    public String getStore(@PathVariable("id") Long id, Model model){
        Store store = storeService.getStoreById(id);
        if (model.containsAttribute("addCartDetailList")) {
            model.addAttribute("addCartDetailList", model.asMap().get("addCartDetailList"));
            model.addAttribute("cart", model.asMap().get("cart"));
            model.addAttribute("message", model.asMap().get("message"));
        }
        model.addAttribute("store", store);
        model.addAttribute("css", "/assetss/css/productDetail.css");
        return "assetss/form/productDetail";
    }

}
