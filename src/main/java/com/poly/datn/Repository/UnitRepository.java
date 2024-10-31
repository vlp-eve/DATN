package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Product1;
import com.poly.datn.Entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    @Query("SELECT p FROM Unit p WHERE p.id = ?1")
    Unit findUnitById(Long id);

    List<Unit> findByIsDeleteFalse();
}
