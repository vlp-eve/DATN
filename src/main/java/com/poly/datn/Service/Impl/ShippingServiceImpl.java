package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Payment.Method;
import com.poly.datn.Entity.Payment.Shipping;
import com.poly.datn.Repository.OrderRepository;
import com.poly.datn.Repository.ShippingRepository;
import com.poly.datn.Service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;

public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void addShippingAndMethodPay(Long orderId, Shipping shipping, Method method){
        try {
            Order order = orderRepository.getOrderById(orderId);
            shipping.setOrder(order);
            shipping.setAddress(order.getUser().getAddress());
            shipping.setPhone(order.getUser().getPhone());
            shipping.setNote(shipping.getNote());
            shippingRepository.save(shipping);
            order.setMethod(method);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
