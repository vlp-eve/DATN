package com.poly.datn.Service;

import com.poly.datn.Entity.Cart.Cart;

import java.util.List;
import java.util.Optional;

public interface CartService {
    public void removeProductFromCart(Long cartId, Long cartDetailId);
    public Cart updateCart(Long id, Cart cartDetails);
    public List<Cart> getAllCarts();
    public Optional<Cart> getCartById(Long id);

    public void removeAllProductFromCart(Long cartId);
}
