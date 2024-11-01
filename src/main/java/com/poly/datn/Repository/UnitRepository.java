package com.poly.datn.Repository;


import com.poly.datn.Entity.Product.Unit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    Unit findUnitById(Long id);

    List<Unit> findByIsDeletedFalse();
}
