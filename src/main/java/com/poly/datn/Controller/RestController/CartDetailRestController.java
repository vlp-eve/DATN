package com.poly.datn.Controller.RestController;

import com.poly.datn.Entity.Cart.CartDetail;
import com.poly.datn.Entity.Product.Store;
import com.poly.datn.Entity.StoreId;
import com.poly.datn.Service.CartDetailService;
import com.poly.datn.Service.CartService;
import com.poly.datn.Service.Impl.CartDetailServiceImpl;
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
            @RequestParam Long cartId,
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam Long inventoryId,
            @RequestParam int quantity,
            @RequestParam Double price) {

        StoreId storeId = new StoreId(productId, inventoryId); // Tạo StoreId từ productId và inventoryId
        System.out.println("cartId: " + cartId);
        System.out.println("storeId: " + storeId);
        System.out.println("quantity: " + quantity);
        System.out.println("price: " + price);

        // Gọi service để thêm sản phẩm vào giỏ hàng
        CartDetail cartDetail = cartDetailService.addProductToCart(cartId, userId, productId, inventoryId, quantity, price);

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
