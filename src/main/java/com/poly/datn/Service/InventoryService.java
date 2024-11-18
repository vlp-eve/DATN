package com.poly.datn.Service;

import com.poly.datn.Entity.Product.Inventory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    public void saveInventory(Inventory inventory);
    public void updateInventoryStatusAndDiscount();
    public List<Inventory> getAllInventories();
    public Inventory getInventoryById(Long inventoryId);

    public void deleteInventory(Long inventoryId);
    public void updateInventory(Inventory inventory);

    public List<Inventory> getInventoryByProductId(Long productId);

    public Long getInventoryCount(Long productId);

}
