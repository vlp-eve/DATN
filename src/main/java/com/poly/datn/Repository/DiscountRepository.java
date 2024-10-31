package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Discount;
import com.poly.datn.Entity.Product.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
