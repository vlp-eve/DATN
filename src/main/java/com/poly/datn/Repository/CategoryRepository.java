package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryById(Long id);
    List<Category> findByIsDeletedFalse();

    List<Category> findByIsDeletedTrue();
}
