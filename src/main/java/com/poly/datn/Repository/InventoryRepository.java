package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByIsDeletedFalse();


    Inventory findInventoryById(Long id);

    List<Inventory> findByProduct_Id(Long productId);


    List<Inventory> findByProduct_IdAndIsDeletedFalse(Long productId);
//    Tìm lô hàng sắp hết hạn
    @Query("SELECT i FROM Inventory i WHERE i.expiryDate BETWEEN :startDate AND :endDate AND i.isDeleted = 0 ORDER BY i.expiryDate DESC")
    List<Inventory> findProductsExpiringWithinDateRange(LocalDate startDate, LocalDate endDate);
}
