package com.poly.datn.Controller;

import com.poly.datn.Entity.Product.Product;

import com.poly.datn.Entity.Product.ProductImg;
import com.poly.datn.Service.Impl.Product.ProductImgServiceImpl;


import com.poly.datn.Service.Product1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/productImg")
public class  ProductImgController {

    @Autowired
    private Product1Service product1Service;

    @Autowired
    private ProductImgServiceImpl productImgService;

    @GetMapping("/{productId}")
    public String showAddImagesPage(@PathVariable Long productId, Model model) {
        Product product = product1Service.getById(productId);
        List<ProductImg> productImg = productImgService.getALlProductImgById(productId);

        model.addAttribute("product", product);
        model.addAttribute("images", productImg);

        return "testProductImg"; // Trả về tên trang HTML để hiển thị
    }

    @PostMapping("/{productId}/addImages")
    public String addImages(@PathVariable Long productId,
                            @RequestParam("files") MultipartFile[] files,
                            Model model, RedirectAttributes redirectAttributes) {
        Product product = product1Service.getById(productId);
        List<ProductImg> productImages = new ArrayList<>();


        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // Lưu ảnh vào hệ thống  và lấy đường dẫn
                String imgPath = saveImage(file);
                ProductImg productImg = new ProductImg();
                productImg.setProduct(product);
                productImg.setImg(imgPath);
                productImages.add(productImg);
            }
        }


        productImgService.saveAll(productImages);

        model.addAttribute("product", product);
        model.addAttribute("images", productImgService.getALlProductImgById(productId));
        model.addAttribute("success", "Images added successfully!");
        return "redirect:/productImg/{productId}"; // Trả về tên trang HTML để hiển thị
    }

    private String saveImage(MultipartFile file) {
        // Lưu ảnh vào hệ thống tệp và trả về đường dẫn đến ảnh
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        ClassLoader classLoader = getClass().getClassLoader();
        File uploadDir = new File(classLoader.getResource("static/img/img_product/").getFile()); // lấy ở target

        Path filePath = Paths.get(String.valueOf(uploadDir), fileName);
        try {
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Error saving file", e);
        }
        return "/img/img_product/" + fileName;
    }
}
