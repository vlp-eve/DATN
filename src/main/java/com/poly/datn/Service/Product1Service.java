package com.poly.datn.Service;

import com.poly.datn.Entity.Product.Product;
import com.poly.datn.Entity.Product.Product1;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface Product1Service {
    public List<Product> getNonDeletedProducts();
//    public List<Product1> getDeletedProducts();
    public void softDeleteProduct(Long productId);
    public void addProduct(Product product, MultipartFile file);
    public void updateProducts(Long id, Product product1, MultipartFile file);
    public Product getById(long id);
    public List<Product> getAllProducts();

}
