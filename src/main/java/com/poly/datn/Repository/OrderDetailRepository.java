package com.poly.datn.Repository;

import com.poly.datn.Entity.Order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<Long, OrderDetail> {
}
