package com.poly.datn.Repository;

import com.poly.datn.Entity.Cart.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByIdAndUser_Id(Long cartId, Long userId);
    Cart getByUser_Id(Long userId);

    Cart findByUserId(Long userId);

}
