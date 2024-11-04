package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Payment.Method;
import com.poly.datn.Entity.Payment.Shipping;
import com.poly.datn.Repository.MethodRepository;
import com.poly.datn.Repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MethodServiceImpl {
@Autowired
    private MethodRepository methodRepository;

@Autowired
    private ShippingRepository shippingRepository;
public void  chosseMethod(Long idShipping){
    Shipping shipping = shippingRepository.getReferenceById(idShipping);
    if(shipping.getMethod().equals("Chuyển khoản") ){

    }
}



}
