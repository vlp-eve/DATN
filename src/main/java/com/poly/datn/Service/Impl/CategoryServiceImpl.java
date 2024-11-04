package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.IsDelete;
import com.poly.datn.Entity.Product.Category;

import com.poly.datn.Repository.CategoryRepository;
import com.poly.datn.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
// Tìm tất cả danh mục khi dã bị xóa
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
//  Tìm danh mục chưa bị xóa
    public List<Category> getNonDeleteCategory() {
        return categoryRepository.findByIsDeletedFalse();
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        try {
        Category category = categoryRepository.findCategoryById(id);
        if (category == null){
            throw new NoSuchElementException("Không tìm thấy category với ID: " + id);
        }
        category.setName(categoryDetails.getName());
        return categoryRepository.save(category);
        }catch (NoSuchElementException e) {
            throw new RuntimeException("Lỗi: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Lỗi truy xuất cơ sở dữ liệu", e);
        } catch (Exception e) {
            throw new RuntimeException("Đã xảy ra lỗi không xác định", e);
        }
    }

    public void deleteCategory(Long id) {
        try {
            Category category = categoryRepository.findCategoryById(id);
            if (category == null){
                throw new NoSuchElementException("Không tìm thấy category với ID: " +id);
            }
            if(category.getIsDeleted() == IsDelete.ACTIVE.getValue()) {
                category.setIsDeleted(IsDelete.DELETED.getValue());
                categoryRepository.save(category);
            }else {
                System.out.println("category đã bị xóa trước đó");
            }
        }catch (NoSuchElementException e) {
            throw new RuntimeException("Lỗi: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Lỗi truy xuất cơ sở dữ liệu", e);
        } catch (Exception e) {
            throw new RuntimeException("Đã xảy ra lỗi không xác định", e);
        }

    }

    @Override
    public Category findCategoryById(Long id) {
        return categoryRepository.findCategoryById(id);
    }

    @Override
    public List<Category> getNonDeletedCategory() {
        return categoryRepository.findByIsDeletedFalse();
    }
}
