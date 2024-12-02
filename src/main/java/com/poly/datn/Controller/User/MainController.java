package com.poly.datn.Controller.User;

import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.Cart.CartDetail;
import com.poly.datn.Entity.Product.Category;
import com.poly.datn.Entity.User.User;
import com.poly.datn.Service.CartService;
import com.poly.datn.Service.CategoryService;
import com.poly.datn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MainController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

//    @ModelAttribute("cartQuantity")
//    public int getCartQuantity1(Principal principal) {
//        if (principal != null) {
//            String username = ((UserDetails) principal).getUsername();
//            User user = userService.findByUsernameUser(username);
//            Cart userCart = cartService.getCartByUser(user);
//            int quantity = userCart != null ? userCart.getCartDetails().stream().mapToInt(CartDetail::getQuantity).sum() : 0;
//            System.out.println("Cart Quantity for user " + username + ": " + quantity); // Ghi log số lượng giỏ hàng
//            return quantity;
//        }
//        System.out.println("Cart Quantity: 0 (not logged in)"); // Ghi log khi chưa đăng nhập
//        return 0;
//    }
@ModelAttribute("cartQuantity")
public int getCartQuantity(Principal principal) {
    if (principal != null) {
        if (principal instanceof Authentication authentication) {
            Object principalObject = authentication.getPrincipal();

            if (principalObject instanceof UserDetails userDetails) {
                String username = userDetails.getUsername();
                User user = userService.findByUsernameUser(username);
                if (user != null) {
                    Cart userCart = cartService.getCartByUser(user);
                    if (userCart != null) {
                        // Kiểm tra và khởi tạo cartDetails nếu nó là null
                        List<CartDetail> cartDetails = userCart.getCartDetails();
                        if (cartDetails == null) {
                            cartDetails = new ArrayList<>();
                        }

                        // Đếm số lượng sản phẩm trong giỏ hàng
                        int productCount = (int) cartDetails.stream()
                                .map(CartDetail::getStore)
                                .distinct()
                                .count();

                        System.out.println("Cart Product Count for user " + username + ": " + productCount);
                        return productCount;

                    }
                }
            }
        }
    }
    System.out.println("Cart Quantity: 0 (not logged in or no cart)");
    return 0;
}


    @ModelAttribute("category")
    public List<Category> getCategory(){
        List<Category>  list = categoryService.getNonDeletedCategory();
        System.out.println(list);
        return list;
    }

    @ModelAttribute("username")
    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
