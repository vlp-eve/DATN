package com.poly.datn.Controller.RestController;

import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.Cart.CartDetail;

import com.poly.datn.Service.CartService;
import com.poly.datn.Service.CartDetailService;
import com.poly.datn.Service.Product1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartRestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private Product1Service product1Service;
    // Lấy tất cả giỏ hàng


    // Lấy giỏ hàng theo ID
    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Long id) {
        return cartService.getCartById(id);


    }

    // Thêm giỏ hàng mới
//    @PostMapping
//    public Cart createCart(@RequestBody Cart cart) {
//        return cartService.saveCart(cart);
//    }

    // Cập nhật giỏ hàng
    @PutMapping("/{id}")
    public ResponseEntity<Cart> updateCart(@PathVariable Long id, @RequestBody Cart cartDetails) {
        Cart updatedCart = cartService.updateCart(id, cartDetails);
        return ResponseEntity.ok(updatedCart);
    }

//    // Xóa giỏ hàng
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
//        cartService.deleteCart(id);
//        return ResponseEntity.noContent().build();
//    }

//    // Thêm sản phẩm vào giỏ hàng
//    @PostMapping("/{cartId}/details")
//    public CartDetail addProductToCart(@PathVariable Long cartId, @RequestBody CartDetail cartDetail) {
//        return cartDetailService.addProductToCart(cartDetail);
//    }

//    // Xóa sản phẩm khỏi giỏ hàng
//    @DeleteMapping("/{cartId}/details/{detailId}")
//    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long cartId, @PathVariable Long detailId) {
//        cartDetailService.deleteCartDetail(detailId);
//        return ResponseEntity.noContent().build();
//    }

    // Lấy chi tiết giỏ hàng
    @GetMapping("/{cartId}/details")
    public List<CartDetail> getCartDetails(@PathVariable Long cartId) {
        return cartDetailService.getAllCartDetailsForCart(cartId);
    }
//    @PostMapping("/{cartId}/add-product")
//    public ResponseEntity<CartDetail> addProductToCart(
//            @PathVariable Long cartId,
//            @RequestParam Long productId,
//            @RequestParam Long inventoryId,
//            @RequestParam int quantity,
//            @RequestParam Double price) {
//        Long userId = Long.valueOf(1);
//
//        CartDetail cartDetail = cartDetailService.addProductToCart(cartId, userId, productId, inventoryId, quantity, price  );
//        return ResponseEntity.ok(cartDetail);
//    }
//    @PostMapping("/{cartId}/add-product")
//    public ResponseEntity<CartDetail> addProductToCart(
//            @PathVariable Long cartId,
//            @RequestParam Long productId,
//            @RequestParam Long inventoryId,
//            @RequestParam Long unitId,
//            @RequestParam int quantity
//           ) {
//        Long userId = Long.valueOf(1);
//        Product product1 = product1Service.getById(productId);
//        Double price1 = product1.getPrice();
//        CartDetail cartDetail = cartDetailService.addProductToCart(cartId, productId, inventoryId, unitId, quantity, price1);
//        return ResponseEntity.ok(cartDetail);
//    }


}
