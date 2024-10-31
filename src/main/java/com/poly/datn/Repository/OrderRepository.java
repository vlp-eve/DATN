package com.poly.datn.Repository;

import com.poly.datn.Entity.Order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Long, Order> {
}
