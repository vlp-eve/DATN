package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Product.Category;
import com.poly.datn.Entity.Product.Product1;
import com.poly.datn.Repository.CategoryRepository;
import com.poly.datn.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }



    public List<Category> getNonDeleteCategory() {
        return categoryRepository.findByIsDeleteFalse();
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDetails.getName());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findCategoryById(id);
        category.setDelete(true);
        categoryRepository.save(category);
    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findCategoryById(id);
    }

    @Override
    public List<Category> getNonDeletedCategory() {
        return categoryRepository.findByIsDeleteFalse();
    }
}
