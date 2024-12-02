package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryById(Long id);
    List<Category> findByIsDeletedFalse();

    Category findCategoryByIdAndIsDeletedFalse(Long id);
    List<Category> findByIsDeletedTrue();
    @Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
    List<Category> searchCategoryName(String keyword);
    Category getById(Long id);
}
