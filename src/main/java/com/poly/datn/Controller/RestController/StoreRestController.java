package com.poly.datn.Controller.RestController;

import com.poly.datn.Entity.DTO.Product1Dto;
import com.poly.datn.Entity.DTO.StoreDTO;
import com.poly.datn.Entity.Product.Discount;
import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product;
import com.poly.datn.Entity.Product.Store;
import com.poly.datn.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreRestController {

    @Autowired
    private StoreService storeService;

    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.findAllStore();
        return ResponseEntity.ok(stores);
    }


    @GetMapping("/valid-inventory")
    public ResponseEntity<List<StoreDTO>> getStoresWithValidInventory() {
        List<Object[]> results = storeService.getProductsWithUpcomingExpiry();
        List<StoreDTO> storeResponses = new ArrayList<>();

        for (Object[] result : results) {
            Long storeId = (Long) result[0];
            Discount discount = (Discount) result[1];
            Product product = (Product) result[2];
            Inventory inventory = (Inventory) result[3];
            // Tạo đối tượng response
            storeResponses.add(new StoreDTO(storeId, discount, product, inventory));
        }

        return ResponseEntity.ok(storeResponses);
    }
}
