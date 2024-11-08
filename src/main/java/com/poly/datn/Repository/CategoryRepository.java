package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Category;

import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryById(Long id);
    List<Category> findByIsDeletedFalse();

    Category findCategoryByIdAndIsDeletedFalse(Long id);
    List<Category> findByIsDeletedTrue();

}
