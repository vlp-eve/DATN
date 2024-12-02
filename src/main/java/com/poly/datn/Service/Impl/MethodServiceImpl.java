package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Payment.Method;
import com.poly.datn.Repository.MethodRepository;
import com.poly.datn.Repository.ShippingRepository;
import com.poly.datn.Service.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class MethodServiceImpl implements MethodService {
    @Autowired
    private MethodRepository methodRepository;

    @Autowired
    private ShippingRepository shippingRepository;

    public List<Method> getAllMethod(){
        return methodRepository.findAll();
    }

    public Method getMethodById(Long methodId){
        return methodRepository.getReferenceById(methodId);
    }



}
