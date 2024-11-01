package com.poly.datn.Service.Impl.Product;

import com.poly.datn.Entity.IsDelete;
import com.poly.datn.Entity.Product.Product;

import com.poly.datn.Repository.DiscountRepository;
import com.poly.datn.Repository.InventoryRepository;
import com.poly.datn.Repository.Product1Repository;
import com.poly.datn.Repository.StatusRepository;
import com.poly.datn.Service.Product1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductServiceImpl implements Product1Service {
    @Autowired
    private Product1Repository product1Repository;
    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    private LocalDate today = LocalDate.now();
    public void addProduct(Product product, MultipartFile file) {

        product.setCreatedAt(today);
        product.setUpdateAt(today);
        product.setTotalQuantity(0);
        // Xử lý file ảnh và lưu đường dẫn file
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("Not a valid file");
            return;
        }

        // Đường dẫn thư mục lưu file
//        String uploadDir = new File("src/main/resources/static/img/img_product/").getAbsolutePath(); // tạo thằng 1 file mới
        ClassLoader classLoader = getClass().getClassLoader();
        File uploadDir = new File(classLoader.getResource("static/img/img_product/").getFile()); // lấy ở target
        Path filePath = Paths.get(String.valueOf(uploadDir), fileName);
        try {
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            product.setImgBannerPath("/img/img_product/" + fileName);
            System.out.println(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Error saving file", e);
        }
        product1Repository.save(product);
    }


    // tìm tất cả product cho dù bị xóa mềm
    public List<Product> getAllProducts() {
        return product1Repository.findAll();
    }




    public void updateProducts(Long id, Product product, MultipartFile file){
        Product product1 = product1Repository.findProductById(id);
        product1.setUpdateAt(today);
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        //  xử lý việc lưu trữ file
        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (fileName.contains("..")) {
                System.out.println("Not a valid file");
                return;
            }
            ClassLoader classLoader = getClass().getClassLoader();
            File uploadDir = new File(classLoader.getResource("static/img/img_product/").getFile()); // lấy ở target

            Path filePath = Paths.get(String.valueOf(uploadDir), fileName);
            try {
                Files.createDirectories(filePath.getParent());
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Lưu đường dẫn tương đối vào cơ sở dữ liệu
                product1.setImgBannerPath("/img/img_product/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Error saving file", e);
            }
        } else {
            System.out.println("Không thay đổi ảnh");
        }
//        product.setAvailable(product1.isAvailable());
        product1Repository.save(product1);
    }


    public Product getById(long id){
        return product1Repository.findProductById(id);
    }


    // xóa mềm
    public void softDeleteProduct(Long productId) {
        Product product = product1Repository.findProductById(productId);
        product.setIsDeleted(IsDelete.DELETED.getValue());
        product.setUpdateAt(today);
        product1Repository.save(product);
    }

    // lấy danh sách chưa bị xóa mềm
    public List<Product> getNonDeletedProducts() {
        return product1Repository.findByIsDeletedFalse();
    }





}