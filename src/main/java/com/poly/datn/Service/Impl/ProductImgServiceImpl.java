package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Product.ProductImg;

import com.poly.datn.Repository.ProductImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImgServiceImpl {

    @Autowired
    private ProductImgRepository productImgRepository;

    public void saveAll(List<ProductImg> productImages) {
        productImgRepository.saveAll(productImages);
    }
    public List<ProductImg> getALlProductImgById(Long id) {
        return productImgRepository.findAllByProduct1_ProductId(id);
    }
}
