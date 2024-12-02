package com.poly.datn.Controller.Admin;

import com.poly.datn.Entity.IsDelete;
import com.poly.datn.Entity.Product.Category;
import com.poly.datn.Service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("css", "/assets/css/editCategory.css");
        model.addAttribute("category", new Category());
        return "assets/form/editCategory";
    }

    // Thêm mới danh mục
    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("css", "/assets/css/editCategory.css");
        model.addAttribute("category", new Category());
        return "assets/form/editCategory";
    }

    // Thêm mới danh mục
    @PostMapping("/save")
    public String save(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        try {
            categoryService.addCategory(category);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm mới danh mục thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi thêm mới danh mục: " + e.getMessage());
        }
        return "redirect:/admin/category/add";
    }

    // Hiển thị form chỉnh sửa danh mục
    @GetMapping("/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model) {
        Optional<Category> existingCategory = categoryService.findById(id);

        if (existingCategory.isEmpty()) {
            model.addAttribute("errorMessage", "Danh mục không tồn tại.");
            return "redirect:/admin/category/list";
        }

        Category category = existingCategory.get();
        model.addAttribute("category", category);
        return "assets/form/editCategory";
    }

    // Cập nhật danh mục
    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Long id,@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        try {
            Category existingCategory = categoryService.getById(id);

            if (existingCategory == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy danh mục cần cập nhật.");
                return "redirect:/admin/category/list";
            }

            categoryService.updateCategory(id, existingCategory);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật danh mục thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi khi cập nhật danh mục: " + e.getMessage());
        }
        return "redirect:/admin/category/list";
    }

    // Hiển thị danh sách danh mục
    @GetMapping("/list")
    public String showList(Model model,
                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                           @RequestParam(name = "keyword", required = false) String keyword) {
        Page<Category> list;

        if (keyword != null && !keyword.isEmpty()) {
            list = categoryService.searchCategory(keyword, pageNo);
            model.addAttribute("keyword", keyword);
        } else {
            list = categoryService.findAll(pageNo);
        }

        model.addAttribute("categories", list);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("css", "/assets/css/listCategory.css");
        return "assets/form/listCategory";
    }

    // Sửa danh mục (hiển thị form chỉnh sửa)
    @GetMapping("/form/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Optional<Category> category = categoryService.findById(id);
        model.addAttribute("category", category.orElse(new Category()));
        model.addAttribute("css", "/assets/css/editCategory.css");
        return "assets/form/editCategory";
    }
    @PostMapping("/delete/{id}")
    public String softDeleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategory(id);

            redirectAttributes.addFlashAttribute("successMessage", "Xóa danh mục thành công.");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Danh mục không tồn tại hoặc đã bị xóa.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Xóa danh mục thất bại. Đã xảy ra lỗi.");
        }
        return "redirect:/admin/category/list";
    }
}
