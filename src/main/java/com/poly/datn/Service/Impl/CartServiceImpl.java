package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.Cart.CartDetail;
import com.poly.datn.Entity.User.User;
import com.poly.datn.Repository.CartDetailRepository;
import com.poly.datn.Repository.CartRepository;
import com.poly.datn.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;



    public Cart getCartById(Long id) {
        return cartRepository.getById(id);
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


//    Xóa tất cản sản phẩm trong giỏ hàng
@Override
public void removeAllProductFromCart(Long cartId) {
    // Tìm tất cả CartDetail theo cartId
    List<CartDetail> cartDetails = cartDetailRepository.findByCartId(cartId);

    // Kiểm tra nếu có CartDetail
    if (cartDetails != null && !cartDetails.isEmpty()) {
        Cart cart = cartDetails.get(0).getCart();

        // Trừ tổng giá của từng sản phẩm trong giỏ hàng
        for (CartDetail cartDetail : cartDetails) {
            Double totalPriceToRemove = cartDetail.getPrice() * cartDetail.getQuantity();
            cart.setTotalPrice(cart.getTotalPrice() - totalPriceToRemove);
        }

        // Lưu lại giỏ hàng sau khi cập nhật tổng giá trị
        cartRepository.save(cart);

        // Xóa tất cả CartDetail
        cartDetailRepository.deleteAll(cartDetails);
    }
}


    @Override
    public Cart getCartByUser(User user) {

        Cart optionalCart = cartRepository.findByUserId(user.getId());
        if (!(optionalCart == null)) {
            return optionalCart;
        } else {
            // Tạo giỏ hàng mới nếu chưa tồn tại
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setTotalPrice(0.0);
            newCart.setCreateDate(LocalDate.now()); // Ngày tạo
            return cartRepository.save(newCart);
        }
    }
}
