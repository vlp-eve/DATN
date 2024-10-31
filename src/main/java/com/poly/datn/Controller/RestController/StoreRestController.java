package com.poly.datn.Controller.RestController;

import com.poly.datn.Entity.DTO.Product1Dto;
import com.poly.datn.Entity.Product.Product1;
import com.poly.datn.Entity.Product.Store;
import com.poly.datn.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreRestController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.findAllStore();
//        storeService.setDiscount();
        return ResponseEntity.ok(stores);
    }
    @GetMapping("/update")
    public void getAllStoresdiscount() {
        storeService.setDiscount();

    }
    @GetMapping("/available-products")
    public List<Product1Dto> getAvailableProducts() {
        return storeService.findAvailableProductsFromStore();
    }

    @GetMapping("/products")
    public List<Store> getProductsWithUpcomingExpiry() {
        return storeService.getProductsWithUpcomingExpiry();
    }

}
