package com.poly.datn.Repository;

import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Product.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order getOrderById (Long OrderId);
    List<Order> getOrderByUser_Id(Long userId);

    @Query("SELECT od.store.product FROM OrderDetail od GROUP BY od.store.product ORDER BY SUM(od.quantity) DESC LIMIT 5")
    List<Product> findTop5BestSellingProducts();

    @Query("SELECT od.store.product FROM OrderDetail od GROUP BY od.store.product ORDER BY SUM(od.quantity) ASC LIMIT 5")
    List<Product> findTop5LowSellingProducts();

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.shipping WHERE o.id = :orderId")
    List<Order> findOrderWithShippingById(@Param("orderId") Long orderId);


}
