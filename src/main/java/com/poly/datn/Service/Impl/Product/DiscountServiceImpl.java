package com.poly.datn.Service.Impl.Product;

import com.poly.datn.Entity.Product.Discount;

import com.poly.datn.Repository.DiscountRepository;
import com.poly.datn.Repository.Product1Repository;
import com.poly.datn.Repository.StatusRepository;
import com.poly.datn.Service.DiscountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {


    @Autowired
    private DiscountRepository discountRepository;


    public Discount addDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

}
