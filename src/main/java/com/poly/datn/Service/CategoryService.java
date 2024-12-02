package com.poly.datn.Service;

import com.poly.datn.Entity.Product.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {

    public Page<Category> findAll(Integer pageNo);
    public Page<Category> searchCategory(String keyword, Integer pageNo);
    public List<Category> searchCategory(String keyword);
     List<Category> getAllCategories();
    Optional<Category> findById(Long id);
    public Category addCategory(Category category);




    public Category updateCategory(Long id, Category categoryDetails);


    public void deleteCategory(Long id);

    public Category findCategoryById(Long id);

    public List<Category>  getNonDeletedCategory();
    public Category getById(Long id);
}
