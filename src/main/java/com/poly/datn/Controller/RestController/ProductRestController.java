//package com.poly.datn.Controller.RestController;
//
//import com.poly.datn.Entity.Product.Product1;
//import com.poly.datn.Service.InventoryService;
//import com.poly.datn.Service.Product1Service;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/products")
//public class ProductRestController {
//
//    @Autowired
//    private Product1Service productService;
//
//    @Autowired
//    private InventoryService inventoryService;
//
//
//    @GetMapping
//    public List<Product1> getAllProducts() {
//        return productService.getProductsNearExpiry();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Product1> getProductById(@PathVariable Long id) {
//        Product1 product = productService.getById(id);
//        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
//    }
//
//    @PostMapping
//    public HttpStatus createProduct(@RequestBody Product1 product, MultipartFile multipartFile) {
//        productService.addProduct(product, multipartFile);
//        return HttpStatus.OK;
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Product1> updateProduct(@PathVariable Long id, @RequestBody Product1 productDetails, @RequestParam("file")MultipartFile multipartFile) {
//        Product1 product = productService.getProductById(id);
//        if (product != null) {
//            productService.updateProducts(id, productDetails, multipartFile);
//            return ResponseEntity.ok(product);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//        productService.softDeleteProduct(id);
//        return ResponseEntity.noContent().build();
//    }
//
//
//    // xử lí tìm lô hàng phù hợp
//    @PostMapping("/process/{productId}/{quantity}")
//    public ResponseEntity<String> processInventory(@PathVariable Long productId, @PathVariable int quantity) {
//        try {
//            inventoryService.processNearestExpiryInventory(productId, quantity);
//            return ResponseEntity.ok("Đã xử lý thành công lô hàng.");
//        } catch (ConfigDataResourceNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }
//}
