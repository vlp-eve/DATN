package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product1;
import com.poly.datn.Entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
//    @Query("SELECT i FROM Inventory i WHERE i.product.productId = :productId AND i.expiryDate > :now AND i.quantity > 0 ORDER BY i.expiryDate ASC")
//        List<Inventory> findAvailableInventories(Long productId, LocalDate now);
//
    List<Inventory> findByIsDeleteFalse();
//
@Query("SELECT p FROM Inventory p WHERE p.id = ?1 AND p.isDelete = false")
Inventory findInventoryById(Long id);
//
//    @Query("SELECT i FROM Inventory i WHERE i.expiryDate > :now AND i.quantity > 0 ORDER BY i.expiryDate ASC")
//    List<Inventory> findProduct(@Param("now") LocalDate now);
//
    List<Inventory> findByProduct_ProductId(Long productId);
}
