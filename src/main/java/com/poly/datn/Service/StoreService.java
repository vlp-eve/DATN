package com.poly.datn.Service;

import com.poly.datn.Entity.DTO.Product1Dto;

import com.poly.datn.Entity.DTO.StoreDTO;
import com.poly.datn.Entity.Product.Store;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
@Service
public interface StoreService   {
    public List<Store> findAllStore();

    public void setDiscount();
    public List<Object[]> getProductsWithUpcomingExpiry() ;




}
