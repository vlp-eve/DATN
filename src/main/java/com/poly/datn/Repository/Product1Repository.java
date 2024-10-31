package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Product;
import com.poly.datn.Entity.Product.Product1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Product1Repository extends JpaRepository<Product, Long> {


    List<Product> findByIsDeleteFalse();
//
//
//    List<Product1> findByIsDeleteTrue();

    Product findProductById(Long id);
}
