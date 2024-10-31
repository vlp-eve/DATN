package com.poly.datn.Service;

import com.poly.datn.Entity.Product.Discount;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DiscountService {
//    void updateDiscountsForExpiringProducts();
    Discount addDiscount(Discount discount);
    List<Discount> getAllDiscounts();

}

