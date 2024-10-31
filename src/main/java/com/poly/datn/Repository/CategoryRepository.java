package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Category;
import com.poly.datn.Entity.Product.Product1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT p FROM Category p WHERE p.id = ?1")
    Category findCategoryById(Long id);
    List<Category> findByIsDeleteFalse();

    List<Category> findByIsDeleteTrue();
}
