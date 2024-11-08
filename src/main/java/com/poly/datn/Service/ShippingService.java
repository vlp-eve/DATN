package com.poly.datn.Service;

import com.poly.datn.Entity.Payment.Method;
import com.poly.datn.Entity.Payment.Shipping;

public interface ShippingService {
    public void addShippingAndMethodPay(Long orderId, Shipping shipping, Method method);
}
