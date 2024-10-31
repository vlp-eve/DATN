package com.poly.datn.Repository;

import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.Cart.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByIdAndUser_UserId(Long cartId, Long userId);




}
