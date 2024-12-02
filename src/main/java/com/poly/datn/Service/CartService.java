package com.poly.datn.Service;

import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.User.User;

public interface CartService {

    public Cart updateCart(Long id, Cart cartDetails);

    public Cart getCartById(Long id);

    public void removeAllProductFromCart(Long cartId);



    public    Cart getCartByUser(User user);



}
