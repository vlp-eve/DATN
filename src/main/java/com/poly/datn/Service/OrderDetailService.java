package com.poly.datn.Service;

import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Order.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    public Order createOrderFromCart(Long userId, Long methodId);
    public List<OrderDetail> getOrderDetailByOrderId(Long orderId);
    public void processOrderPayment(Long orderId);
}
