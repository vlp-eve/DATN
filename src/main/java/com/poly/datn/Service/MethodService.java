package com.poly.datn.Service;

import com.poly.datn.Entity.Payment.Method;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MethodService {

    public List<Method> getAllMethod();
    public Method getMethodById(Long methodId);
}
