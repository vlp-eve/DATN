package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByIsDeletedFalse();
//

    Inventory findInventoryById(Long id);

    List<Inventory> findByProduct_Id(Long productId);
}
