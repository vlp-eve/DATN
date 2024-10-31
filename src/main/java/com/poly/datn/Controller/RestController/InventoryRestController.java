package com.poly.datn.Controller.RestController;

import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryRestController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAllInventories() {
        return inventoryService.getAllInventories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Inventory inventory = inventoryService.getInventoryById(id);
        return inventory != null ? ResponseEntity.ok(inventory) : ResponseEntity.notFound().build();
    }

//    @PostMapping
//    public Inventory createInventory(@RequestBody Inventory inventory) {
//        return inventoryService.saveInventory(inventory);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventoryDetails) {
//        Inventory inventory = inventoryService.getInventoryById(id);
//        if (inventory != null) {
//            inventory.setQuantity(inventoryDetails.getQuantity());
//            inventory.setExpiryDate(inventoryDetails.getExpiryDate());
//            return ResponseEntity.ok(inventoryService.saveInventory(inventory));
//        }
//        return ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }

    // API để tự động cập nhật trạng thái và giảm giá
    @PostMapping("/update-status-and-discount")
    public ResponseEntity<Void> updateInventoryStatusAndDiscount() {
        inventoryService.updateInventoryStatusAndDiscount();
        return ResponseEntity.ok().build();
    }
}

