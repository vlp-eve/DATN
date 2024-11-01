package com.poly.datn.Controller.RestController;

import com.poly.datn.Entity.Cart.CartDetail;


import com.poly.datn.Service.CartDetailService;
import com.poly.datn.Service.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-details")
public class CartDetailRestController {

    @Autowired
    private CartDetailService cartDetailService;
    @Autowired
    private CartService cartService;

//    Thêm sản phẩm vào cart
    @PostMapping("/add")
    public ResponseEntity<CartDetail> addProductToCart(

            @RequestParam Long userId,
            @RequestParam Long productId,

            @RequestParam int quantity
            ) {


        // Gọi service để thêm sản phẩm vào giỏ hàng
        CartDetail cartDetail = cartDetailService.addProductToCart( userId,  productId,  quantity);

        return ResponseEntity.ok(cartDetail);
    }
    @DeleteMapping("/{cartId}/remove/{cartDetailId}")
    public ResponseEntity<String> removeProductFromCart(
            @PathVariable Long cartId,
            @PathVariable Long cartDetailId) {

        cartService.removeProductFromCart(cartId, cartDetailId);
        return ResponseEntity.ok("Product removed from cart successfully.");
    }
}
