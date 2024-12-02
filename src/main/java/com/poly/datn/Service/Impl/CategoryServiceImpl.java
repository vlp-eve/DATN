package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.IsDelete;
import com.poly.datn.Entity.Product.Category;

import com.poly.datn.Repository.CategoryRepository;
import com.poly.datn.Service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
// Tìm tất cả danh mục khi dã bị xóa
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
//  Tìm danh mục chưa bị xóa




    @Override
    public Category addCategory(Category category) {
        try {
            category.setIsDeleted(IsDelete.ACTIVE.getValue());
            category.setName(category.getName());
             categoryRepository.save(category);
             return category;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lưu danh mục: " + e.getMessage(), e);
        }
    }
    public Category updateCategory(Long id, Category categoryDetails) {
        try {
        Category category = categoryRepository.findCategoryById(id);
        if (category == null){
            throw new EntityNotFoundException("Không tìm thấy category với ID: " + id);
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
            Category category = categoryRepository.findCategoryByIdAndIsDeletedFalse(id);
            if (category == null){
                throw new EntityNotFoundException("Không tìm thấy category chưa bị xóa với ID: " +id);
            }
                category.setIsDeleted(IsDelete.DELETED.getValue());
                categoryRepository.save(category);
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

    @Override
    public Page<Category> findAll(Integer pageNo) {
        // TODO Auto-generated method stub
        Pageable pageable = PageRequest.of(pageNo-1, 2);
        return this.categoryRepository.findAll(pageable);
    }
    @Override
    public Page<Category> searchCategory(String keyword, Integer pageNo) {
        List<Category> list = this.searchCategory(keyword);

        Pageable pageable = PageRequest.of(pageNo-1, 2);

        Integer start = (int) pageable.getOffset();

        Integer end = (int) ((pageable.getOffset()+pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);
        return new PageImpl<Category>(list, pageable, searchCategory(keyword).size());
    }
    @Override
    public List<Category> searchCategory(String keyword) {
        // TODO Auto-generated method stub
        return categoryRepository.searchCategoryName(keyword);
    }
    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.getById(id);
    }
}
