package com.poly.datn.Controller.RestController;


import com.poly.datn.Entity.Product.Product;
import com.poly.datn.Service.Product1Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/products1")
public class Product1RestController {

    @Autowired
    private Product1Service product1Service;
    LocalDate today = LocalDate.now();
    // API để lấy danh sách sản phẩm chưa bị xóa (vẫn còn bán)
    @GetMapping("/non-deleted")
    public List<Product> getNonDeletedProducts() {
        return product1Service.getNonDeletedProducts();
    }

    // API để lấy danh sách sản phẩm đã bị xóa (không còn bán)
    @GetMapping("/all")
    public List<Product> getDeletedProducts() {
        return product1Service.getAllProducts();
    }


    // API để xóa mềm sản phẩm
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> softDeleteProduct(@PathVariable Long productId) {
        try {
            product1Service.softDeleteProduct(productId);
            return ResponseEntity.ok("Product has been soft deleted!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
    }
    // API để thêm sản phẩm

    @PostMapping()
    public ResponseEntity<Product> addProduct(@RequestBody Product product, MultipartFile imgBanner) throws Exception {
        // Kiểm tra dữ liệu và thêm sản phẩm
        Product savedProduct = null;
        savedProduct = product1Service.addProduct(product, imgBanner);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

//    // API để cập nhật sản phẩm
//    @PutMapping("/{productId}")
//    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product productDetails) {
//         product1Service.updateProducts(productId, productDetails);
//        return ResponseEntity.ok(updatedProduct);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = product1Service.getById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
