package com.poly.datn.Repository;

import com.poly.datn.Entity.Product.Product1;
import com.poly.datn.Entity.Product.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Long> {
    List<ProductImg> findAllByProduct1_ProductId(Long productId);

}
