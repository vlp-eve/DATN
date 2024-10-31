package com.poly.datn.Repository;

import com.poly.datn.Entity.DTO.Product1Dto;
import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product1;
import com.poly.datn.Entity.Product.Store;
import com.poly.datn.Entity.StoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Store findByProduct_ProductIdAndInventory_InventoryId(Long productId, Long inventoryId);

    Optional<Object> findById(StoreId storeId);

//    @Query(value = """
//    WITH RankedInventory AS (
//        SELECT
//            i.inventory_id,
//            i.expiry_date,
//            i.product_id,
//            ROW_NUMBER() OVER (
//                PARTITION BY i.product_id
//                ORDER BY i.expiry_date ASC
//            ) AS row_num
//        FROM inventory i
//        JOIN store s ON i.inventory_id = s.inventory_id
//        WHERE i.status_id != 1
//          AND i.is_delete != 1
//          AND i.expiry_date > CURRENT_DATE
//    )
//    SELECT p.product_id, p.name, p.price, p.description, p.img_banner, p.total_quantity, p.available
//    FROM RankedInventory ri
//    JOIN product1 p ON ri.product_id = p.product_id
//    WHERE ri.row_num = 1
//""", nativeQuery = true)
//    List<Product1Dto> findAvailableProductsFromStore();
@Query(value = """
    WITH RankedInventory AS (
        SELECT 
            i.inventory_id, 
            i.expiry_date, 
            i.product_id,
            ROW_NUMBER() OVER (
                PARTITION BY i.product_id 
                ORDER BY i.expiry_date ASC  
            ) AS row_num
        FROM inventory i    
        JOIN store s ON i.inventory_id = s.inventory_id  
        WHERE i.status_id != 1  
          AND i.is_delete != 1
          AND i.expiry_date > CURRENT_DATE  
    )
    SELECT  p.product_id, p.name, p.price, p.description, p.img_banner, p.total_quantity, p.available, ri.inventory_id
    FROM RankedInventory ri
    JOIN product1 p ON ri.product_id = p.product_id 
    WHERE ri.row_num = 1
""", nativeQuery = true)
List<Object[]> findAvailableProductsFromStore();

// chuyển sql Obj thành productDTO
    default List<Product1Dto> mapToProduct1Dtos(List<Object[]> results) {
        return results.stream().map(row -> new Product1Dto(
                ((Long) row[0]).longValue(),       // product_id
                (String) row[1],                   // name
                (Double) row[2],                   // price
                (String) row[3],                   // description
                (String) row[4],                   // img_banner
                (Integer) row[5],                  // total_quantity
                (Boolean) row[6],                   // available
                ((Long) row[7]).longValue()            //inventory
        )).collect(Collectors.toList());
    }



    @Query("SELECT s FROM Store s " +
            "WHERE s.inventory.isDelete = false " +
            "AND s.inventory.status.id != 1 " +
            "AND s.inventory.expiryDate > :currentDate " +
            "AND s.inventory.expiryDate = (SELECT MIN(s2.inventory.expiryDate) " +
            "                              FROM Store s2 " +
            "                              WHERE s2.product.id = s.product.id " +
            "                              AND s2.inventory.isDelete = false " +
            "                              AND s2.inventory.status.id != 1) " +
            "ORDER BY s.product.id ASC")
    List<Store> findTopByEachProductAndUpcomingExpiry(@Param("currentDate") LocalDate currentDate);
}
