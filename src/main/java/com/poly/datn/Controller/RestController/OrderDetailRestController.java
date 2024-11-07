package com.poly.datn.Controller.RestController;

import com.poly.datn.Entity.Order.OrderDetail;
import com.poly.datn.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordersDetail")
public class OrderDetailRestController {

    @Autowired
    private OrderDetailService orderDetailService;

    // API tạo đơn hàng từ giỏ hàng
    @PostMapping("/create-from-cart")
    public String createOrderFromCart(@RequestParam Long userId, @RequestParam String address) {
        try {
            orderDetailService.createOrderFromCart(userId, address);
            return "Đơn hàng đã được tạo thành công từ giỏ hàng.";
        } catch (RuntimeException e) {
            return "Không thể tạo đơn hàng: " + e.getMessage();
        }
    }

    // API lấy chi tiết đơn hàng theo orderId
    @GetMapping("/{orderId}/details")
    public List<OrderDetail> getOrderDetailByOrderId(@PathVariable Long orderId) {
        try {
            return orderDetailService.getOrderDetailByOrderId(orderId);
        } catch (RuntimeException e) {
            throw new RuntimeException("Lỗi khi lấy chi tiết đơn hàng: " + e.getMessage());
        }
    }
}
