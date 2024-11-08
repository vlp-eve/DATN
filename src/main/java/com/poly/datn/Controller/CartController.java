package com.poly.datn.Controller;

import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.Cart.CartDetail;
import com.poly.datn.Service.Impl.CartDetailServiceImpl;
import com.poly.datn.Service.Impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private CartDetailServiceImpl cartDetailService;


//    @GetMapping
//    public String getAllCarts(Model model) {
//        List<Cart> carts = cartService.getAllCarts();
//        model.addAttribute("carts", carts);
//        return "cart/cart-list";
//    }

    // Hiển thị giỏ hàng theo ID
    @GetMapping("/{id}")
    public String getCartById(@PathVariable Long id, Model model) {
        Cart cart = cartService.getCartById(id);

            model.addAttribute("cart", cart);
            return "cart/cart-detail";  // trả về view "cart-detail" để hiển thị chi tiết giỏ hàng


    }

    // Tạo giỏ hàng mới
    @PostMapping("/create")
    public String createCart(@ModelAttribute Cart cart, Model model) {
        Cart savedCart = cartService.saveCart(cart);
        model.addAttribute("cart", savedCart);
        return "cart/cart-detail";  // hiển thị chi tiết giỏ hàng mới tạo
    }

    // Cập nhật giỏ hàng
    @PostMapping("/update/{id}")
    public String updateCart(@PathVariable Long id, @ModelAttribute Cart cartDetails, Model model) {
        Cart updatedCart = cartService.updateCart(id, cartDetails);
        model.addAttribute("cart", updatedCart);
        return "cart/cart-detail";  // hiển thị chi tiết giỏ hàng sau khi cập nhật
    }

    // Xóa giỏ hàng
    @GetMapping("/delete/{id}")
    public String deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return "redirect:/cart";  // quay lại trang danh sách giỏ hàng
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add")
    public String addProductToCart(
            @RequestParam Long userId,
            @RequestParam Long storeId,
            @RequestParam int quantity,
            Model model) {
        try {
            CartDetail cartDetail = cartDetailService.addProductToCart(userId, storeId, quantity);
            model.addAttribute("cartDetail", cartDetail);
            return "cart/cart-detail";  // hiển thị chi tiết sản phẩm đã thêm vào giỏ hàng
        } catch (RuntimeException e) {
            model.addAttribute("error", "Không thể thêm sản phẩm vào giỏ hàng");
            return "error";  // hiển thị lỗi nếu có vấn đề
        }
    }

    // Hiển thị chi tiết sản phẩm trong giỏ hàng
    @GetMapping("/details/{cartId}")
    public String getAllCartDetailsForCart(@PathVariable Long cartId, Model model) {
        List<CartDetail> cartDetails = cartDetailService.getAllCartDetailsForCart(cartId);
        model.addAttribute("cartDetails", cartDetails);
        Cart cart = cartService.getCartById(cartId);
        model.addAttribute("cart", cart);
        return "cart";  // hiển thị chi tiết các sản phẩm trong giỏ hàng
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @GetMapping("/remove/{cartId}/{cartDetailId}")
    public String removeProductFromCart(@PathVariable Long cartId, @PathVariable Long cartDetailId) {
        cartService.removeProductFromCart(cartId, cartDetailId);
        return "redirect:/cart/details/" + cartId;  // quay lại trang chi tiết giỏ hàng
    }

    // Xóa tất cả sản phẩm khỏi giỏ hàng
    @GetMapping("/removeAll/{cartId}")
    public String removeAllProductsFromCart(@PathVariable Long cartId) {
        cartService.removeAllProductFromCart(cartId);
        return "redirect:/cart/details/" + cartId;  // quay lại trang chi tiết giỏ hàng
    }
}
