package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Payment.Shipping;
import com.poly.datn.Repository.OrderRepository;
import com.poly.datn.Repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ShippingServiceImpl {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;

    public void addShippingAndMethodPay(Long orderId, Shipping shipping  ){
        try {
            Order order = orderRepository.getOrderById(orderId);
            shipping.setOrder(order);
            shipping.setAddress(order.getUser().getAddress());
            shipping.setPhone(order.getUser().getPhone());
            shipping.setNote(shipping.getNote());
            shipping.setMethod(shipping.getMethod());
            shippingRepository.save(shipping);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }
}
