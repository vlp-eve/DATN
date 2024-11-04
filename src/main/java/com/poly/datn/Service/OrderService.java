package com.poly.datn.Service;

import com.poly.datn.Entity.Order.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public void canceledOrder(Long orderId);
    public Order getOrderById(Long orderId);
    public void confirmedOrder(Long orderId);
    public void resetOldOrder(Long orderId, String address);
    public List<Order> getOrderByUserId(Long userId);
}
