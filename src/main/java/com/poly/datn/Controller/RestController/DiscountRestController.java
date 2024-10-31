package com.poly.datn.Controller.RestController;

import com.poly.datn.Entity.Product.Discount;
import com.poly.datn.Service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountRestController {

    @Autowired
    private DiscountService discountService;

    @GetMapping
    public List<Discount> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Discount> getDiscountById(@PathVariable Long id) {
//        Discount discount = discountService.
//        return discount != null ? ResponseEntity.ok(discount) : ResponseEntity.notFound().build();
//    }

    @PostMapping
    public Discount createDiscount(@RequestBody Discount discount) {
        return discountService.addDiscount(discount);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Discount> updateDiscount(@PathVariable Long id, @RequestBody Discount discountDetails) {
//        Discount discount = discountService.getDiscountById(id);
//        if (discount != null) {
//            discount.setDiscountPercentage(discountDetails.getDiscountPercentage());
//            discount.setStartDate(discountDetails.getStartDate());
//            discount.setEndDate(discountDetails.getEndDate());
//            // Update thêm các thông tin khác
//            return ResponseEntity.ok(discountService.saveDiscount(discount));
//        }
//        return ResponseEntity.notFound().build();
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
//        discountService.deleteDiscount(id);
//        return ResponseEntity.noContent().build();
//    }
}
