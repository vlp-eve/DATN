package com.poly.datn.Service;

import com.poly.datn.Entity.Cart.CartDetail;

import java.util.List;
import java.util.Optional;

public interface CartDetailService {
    public CartDetail updateCartDetail(Long id, CartDetail cartDetailDetails);
    public CartDetail addProductToCart(Long cartId, Long userId, Long productId, Long inventoryId, int quantity, Double price);

//    void deleteById(Long cartDetailId);
    public List<CartDetail> getAllCartDetailsForCart(Long cartId);
}
