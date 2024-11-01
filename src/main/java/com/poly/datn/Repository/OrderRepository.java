package com.poly.datn.Repository;

import com.poly.datn.Entity.Order.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order getOrderById (Long OrderId);
}
