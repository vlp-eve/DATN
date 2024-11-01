package com.poly.datn.Controller;

import com.poly.datn.Entity.Product.Category;
import com.poly.datn.Entity.Product.Product;


import com.poly.datn.Entity.Product.Unit;

import com.poly.datn.Service.CategoryService;
import com.poly.datn.Service.Product1Service;
import com.poly.datn.Service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private Product1Service product1Service;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UnitService unitService;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = product1Service.getAllProducts();
        model.addAttribute("products", products);
        return "product/list";
    }

    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());

        // Lấy danh sách Category và Unit để hiển thị trong form
        List<Category> categories = categoryService.getAllCategories();
        List<Unit> units = unitService.getAllUnit();
        model.addAttribute("categories", categories);
        model.addAttribute("units", units);

        return "product/add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile file) {
        product1Service.addProduct(product, file);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = product1Service.getById(id);
        model.addAttribute("product", product);

        // Lấy danh sách Category và Unit để hiển thị trong form
        List<Category> categories = categoryService.getAllCategories();
        List<Unit> units = unitService.getAllUnit();
        model.addAttribute("categories", categories);
        model.addAttribute("units", units);

        return "product/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @ModelAttribute Product product,
                                @RequestParam("file") MultipartFile file) {
        product1Service.updateProducts(id, product, file);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        product1Service.softDeleteProduct(id);
        return "redirect:/products";
    }
}
