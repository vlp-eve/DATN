package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.StatusOrder;
import com.poly.datn.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceImpl {



    @Autowired
    private OrderRepository orderRepository;





    public Order getOrderById(Long orderId){
        return orderRepository.getOrderById(orderId);
    }



    public void canceledOrder(Long orderId){
        Order order = getOrderById(orderId);
        String status = order.getStatus().name();
        if(status == StatusOrder.PENDING.name() || status == StatusOrder.CONFIRMED.name() || status == StatusOrder.PICKING.name()) {
            order.setStatus(StatusOrder.CANCELED);
            orderRepository.save(order);
        }else {
            System.out.println("Không thể hủy đơn hàng");
        }
    }
}
