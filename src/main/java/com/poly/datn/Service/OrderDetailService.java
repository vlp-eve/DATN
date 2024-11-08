package com.poly.datn.Service;

import com.poly.datn.Entity.Order.OrderDetail;
import com.poly.datn.Entity.Payment.Shipping;

import java.util.List;

public interface OrderDetailService {
    public void createOrderFromCart(Long userId, Long methodId);
    public List<OrderDetail> getOrderDetailByOrderId(Long orderId);

}
