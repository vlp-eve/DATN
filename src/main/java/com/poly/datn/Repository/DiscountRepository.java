package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Discount;

import org.springframework.data.jpa.repository.JpaRepository;



public interface DiscountRepository extends JpaRepository<Discount, Long> {
            Discount getDiscountById(Long id);

}
