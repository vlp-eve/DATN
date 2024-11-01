package com.poly.datn.Service;

import com.poly.datn.Entity.Order.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    public void canceledOrder(Long orderId);
    public Order getOrderById(Long orderId);
}
