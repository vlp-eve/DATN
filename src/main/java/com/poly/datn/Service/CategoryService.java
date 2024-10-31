package com.poly.datn.Service;

import com.poly.datn.Entity.Product.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {


     List<Category> getAllCategories();



    public Category addCategory(Category category);



    public Category updateCategory(Long id, Category categoryDetails);


    public void deleteCategory(Long id);

    public Category findCategoryById(Long id);

    public List<Category>  getNonDeletedCategory();
}
