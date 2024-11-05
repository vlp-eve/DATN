package com.poly.datn.Controller.RestController;


import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    // Lấy tất cả đơn hàng
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrder();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Lấy đơn hàng theo ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Lấy đơn hàng của người dùng theo User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrderByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrderByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Mua lại từ đơn hàng cũ
    @PostMapping("/reset/{orderId}")
    public ResponseEntity<String> resetOldOrder(@PathVariable Long orderId, @RequestParam(required = false) String address) {
        orderService.resetOldOrder(orderId, address);
        return new ResponseEntity<>("Mua lại đơn hàng thành công", HttpStatus.CREATED);
    }

    // Hủy đơn hàng
    @PutMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.canceledOrder(orderId);
        return new ResponseEntity<>("Đơn hàng đã được hủy", HttpStatus.OK);
    }

    // Xác nhận đơn hàng
    @PutMapping("/confirm/{orderId}")
    public ResponseEntity<String> confirmOrder(@PathVariable Long orderId) {
        orderService.confirmedOrder(orderId);
        return new ResponseEntity<>("Đơn hàng đã được xác nhận", HttpStatus.OK);
    }
}
