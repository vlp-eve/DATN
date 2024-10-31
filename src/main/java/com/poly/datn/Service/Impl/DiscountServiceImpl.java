package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Product.Discount;
import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product1;
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
    private Product1Repository productRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private StatusRepository statusRepository;

//    // Phương thức này sẽ được gọi theo lịch trình để cập nhật giảm giá cho sản phẩm gần hết hạn
//    @Scheduled(cron = "0 0 0 * * ?") // Cứ mỗi ngày một lần vào nửa đêm
//    @Override
//    public void updateDiscountsForExpiringProducts() {
//        List<Product1> products = productRepository.findAll();
//        LocalDate today = LocalDate.now();
//
//        for (Product1 product : products) {
//            LocalDate expireDate = product.getExpiryDate();
//            long daysUntilExpiry = expireDate.toEpochDay() - today.toEpochDay();
//            if (daysUntilExpiry <= 0) {
//                product.setDiscount(null);
//                product.setStatus(statusRepository.getReferenceById(1L)); //Hỏng
//            } else if (daysUntilExpiry <= 5) {
//                Discount discount = discountRepository.findById(1L).orElse(null);
//                if (discount != null) {
//                    product.setDiscount(discount);
//                    product.setStatus(statusRepository.getReferenceById(2L));
//                }
//            } else if (daysUntilExpiry <= 10) {
//                Discount discount = discountRepository.findById(2L).orElse(null);
//                if (discount != null) {
//                    product.setDiscount(discount);
//                    product.setStatus(statusRepository.getReferenceById(3L));
//                }
//            } else if( product.getStockQuantity() == 0){
//                product.setDiscount(null);
//                product.setStatus(statusRepository.getReferenceById(4L));
//            }
//            else {
//                product.setDiscount(null); // Không có giảm giá
//                product.setStatus(statusRepository.getReferenceById(5L));
//            }
//
//            productRepository.save(product);
//        }
//    }


    public Discount addDiscount(Discount discount) {
        return discountRepository.save(discount);
    }
//
//
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }


//    // Phương thức áp dụng giảm giá cho lô hàng dựa trên số ngày hết hạn
//    public void applyDiscount(Inventory inventory, int percentage) {
//        Discount discount = new Discount();
//        discount.setInventory(inventory);
//        discount.setDiscountPercentage(percentage);
//        discount.setStartDate(LocalDate.now());
//        discount.setEndDate(inventory.getExpiryDate());  // Giảm giá đến ngày hết hạn
////        discount.setStatus("còn hiệu lực");
//
//        discountRepository.save(discount);
//
//    }

//    public void removeDiscount(Inventory inventory) {
//        List<Discount> discounts = discountRepository.findByInventory(inventory);
//        for (Discount discount : discounts) {
////            discount.setStatus("hết hạn");
//            discountRepository.delete(discount);
//        }
//    }
}
