package com.poly.datn.Service;

import com.poly.datn.Entity.Payment.Shipping;

public interface ShippingService {
    public void addShippingAndMethodPay(Long orderId, String phone, String addRess, String note, Long methodId);


    public Shipping getShippingByOrderId(Long orderId);
}
