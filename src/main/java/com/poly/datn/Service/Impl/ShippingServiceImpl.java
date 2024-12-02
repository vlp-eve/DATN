package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Payment.Shipping;
import com.poly.datn.Repository.MethodRepository;
import com.poly.datn.Repository.OrderRepository;
import com.poly.datn.Repository.ShippingRepository;
import com.poly.datn.Service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MethodRepository methodRepository;
    public void addShippingAndMethodPay(Long orderId, String phone, String addRess, String note, Long methodId){
        try {
            Order order = orderRepository.getOrderById(orderId);
            Shipping shipping = new Shipping();
            shipping.setOrder(order);
            shipping.setAddress(addRess);
            shipping.setPhone(phone);
            shipping.setNote(note);
            shippingRepository.save(shipping);
            order.setMethod(methodRepository.getReferenceById(methodId));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Shipping getShippingByOrderId(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID không được null");
        }
        Shipping shipping = shippingRepository.findByOrder_Id(orderId);
        if (shipping == null) {
            System.out.println("Không tìm thấy thông tin giao hàng cho Order ID: " + orderId);
        } else {
            System.out.println("Shipping Info: " + shipping.toString());
        }
        return shipping;
    }


//    public void addShippingAndMethodPay(Long orderId,Shipping shipping, Long methodId){
//        try {
//            Order order = orderRepository.getOrderById(orderId);
//
//            shipping.setOrder(order);
//            shipping.setAddress(shipping.getAddress());
//            shipping.setPhone(shipping.getPhone());
//            shipping.setNote(shipping.getNote());
//            shippingRepository.save(shipping);
//            order.setMethod(methodRepository.getReferenceById(methodId));
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
}
