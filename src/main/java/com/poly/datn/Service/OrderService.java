package com.poly.datn.Service;


import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Product.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public void canceledOrder(Long orderId);
    public Order getOrderById(Long orderId);
    public void confirmedOrder(Long orderId);
    public void resetOldOrder(Long orderId, String address);
    public List<Order> getOrderByUserId(Long userId);
    public List<Order> getAllOrder();

    public void pickingOrder(Long orderId);

    public void shippedOrder(Long orderId);

    public void deliveredOrder(Long orderId);

    public void unsuccessfulOrder(Long orderId);

    public List<Product> getTop5LowSeller();
    public List<Product> getTop5BestSeller();
}
