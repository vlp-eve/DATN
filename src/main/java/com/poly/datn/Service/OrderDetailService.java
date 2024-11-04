package com.poly.datn.Service;

import com.poly.datn.Entity.Order.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    public void createOrderFromCart(Long userId, String address);
    public List<OrderDetail> getOrderDetailByOrderId(Long orderId);

}
