package com.poly.datn.Repository;


import com.poly.datn.Entity.Product.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface StoreRepository extends JpaRepository<Store, Long> {

    Store findByProduct_IdAndInventory_Id(Long productId, Long inventoryId);

    Store getById(Long storeId);



    @Query("SELECT s.id, d, p, i " +
            "FROM Store s " +
            "JOIN s.inventory i " +
            "JOIN s.product p " +
            "LEFT JOIN s.discount d " +
            "WHERE i.isDeleted = 0 " +
            "AND i.status.id != 1 AND i.status.id != 5 " +
            "AND i.expiryDate > :currentDate " +
            "AND i.expiryDate = (" +
            "    SELECT MIN(i2.expiryDate) " +
            "    FROM Store s2 " +
            "    JOIN s2.inventory i2 " +
            "    WHERE s2.product.id = s.product.id " +
            "    AND i2.isDeleted = 0 " +
            "    AND i2.status.id != 1 AND i2.status.id != 5" +
            ") " +
            "GROUP BY s.id, d, p, i " +
            "ORDER BY SUM(i.quantity) ASC, s.id ASC")
    List<Object[]> findStoresWithValidInventory(@Param("currentDate") LocalDate currentDate);


    @Query("SELECT s.id, d, p, i " +
            "FROM Store s " +
            "JOIN s.inventory i " +
            "JOIN s.product p " +
            "LEFT JOIN s.discount d " +
            "WHERE i.isDeleted = 0 " +
            "AND i.status.id != 1 AND i.status.id != 5 " +
            "AND i.expiryDate > :currentDate " +
            "AND i.expiryDate = (" +
            "    SELECT MIN(i2.expiryDate) " +
            "    FROM Store s2 " +
            "    JOIN s2.inventory i2 " +
            "    WHERE s2.product.id = s.product.id " +
            "    AND i2.isDeleted = 0 " +
            "    AND i2.status.id != 1 AND i2.status.id != 5" +
            ") " +
            "AND p.category.id = :categoryId " +
            "ORDER BY s.product.id ASC")
    List<Object[]> findStoresWithValidInventoryAndCategory(@Param("currentDate") LocalDate currentDate, @Param("categoryId") Long categoryId);


}
