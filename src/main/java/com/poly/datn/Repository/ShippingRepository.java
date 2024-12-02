package com.poly.datn.Repository;

import com.poly.datn.Entity.Payment.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {

    Shipping findByOrder_Id(Long orderId);
}
