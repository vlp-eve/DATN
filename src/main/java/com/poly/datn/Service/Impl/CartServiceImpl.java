package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.Cart.CartDetail;
import com.poly.datn.Repository.CartDetailRepository;
import com.poly.datn.Repository.CartRepository;
import com.poly.datn.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public Cart updateCart(Long id, Cart cartDetails) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            Cart existingCart = optionalCart.get();
            existingCart.setTotalPrice(cartDetails.getTotalPrice());
            existingCart.setCreateDate(cartDetails.getCreateDate());
            return cartRepository.save(existingCart);
        } else {
            throw new RuntimeException("Cart not found with id: " + id);
        }
    }

//    gỡ bỏ sản phẩm khỏi cart
    @Override
    public void removeProductFromCart(Long cartId, Long cartDetailId) {
        Optional<CartDetail> cartDetail = cartDetailRepository.findByCart_IdAndId(cartId, cartDetailId);
        // Xóa sản phẩm khỏi giỏ hàng
        cartRepository.deleteById(cartDetailId);
    }

//    public void removeAllProductFromCart(Long cartId){
//        List<CartDetail> carts = cartDetailRepository.findByCartId(cartId);
//        cartDetailRepository.deleteAllById(carts);
//    }
}
