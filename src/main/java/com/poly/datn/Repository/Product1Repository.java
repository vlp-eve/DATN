package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Product1Repository extends JpaRepository<Product, Long> {


    List<Product> findByIsDeletedFalse();
//
//
//    List<Product1> findByIsDeletedTrue();

    Product findProductById(Long id);

    Product findProductByIdAndIsDeletedFalse(Long id);
}
