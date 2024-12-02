package com.poly.datn.Service;

import com.poly.datn.Entity.Product.Store;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StoreService   {
    public List<Store> findAllStore();

    public void setDiscount();
    public List<Object[]> getProductsWithUpcomingExpiry() ;


    public Store getStoreById(Long id);

    public List<Object[]> getProductsWithUpcomingExpiryAndCategory(Long categoryId);
}
