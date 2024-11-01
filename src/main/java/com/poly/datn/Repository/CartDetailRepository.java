package com.poly.datn.Repository;

import com.poly.datn.Entity.Cart.CartDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    Optional<CartDetail> findByCart_IdAndStore_Id(Long cartId, Long storeId);

    List<CartDetail> findByCartId(Long cartId);
    Optional<CartDetail> findByCart_IdAndId(Long cartId, Long id);


}
