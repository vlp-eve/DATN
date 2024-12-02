package com.poly.datn.Controller.User;

import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Order.OrderDetail;
import com.poly.datn.Entity.Payment.Shipping;
import com.poly.datn.Entity.User.User;
import com.poly.datn.Service.OrderDetailService;
import com.poly.datn.Service.OrderService;
import com.poly.datn.Service.ShippingService;
import com.poly.datn.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orderDetail")
public class OrderDetailUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShippingService shippingService;

    @GetMapping("/{orderId}")
    public String getOrderDetailsByAuthenticatedUser(@PathVariable Long orderId, Model model) {
        model.addAttribute("css", "/assetss/css/orderDetail.css");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userService.findByUsernameUser(username);

                if (user != null) {
                    try {
                        // Lấy danh sách chi tiết đơn hàng theo orderId
                        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByOrderId(orderId);
                        Order order = orderService.getOrderById(orderId);
                        Shipping shipping = shippingService.getShippingByOrderId(orderId);
                        model.addAttribute("shipping", shipping);
                        model.addAttribute("order", order);
                        model.addAttribute("orderDetails", orderDetails);
                        if (model.containsAttribute("successMessage")) {
                            model.addAttribute("successMessage", model.getAttribute("successMessage"));
                        }
                        if (model.containsAttribute("errorMessage")) {
                            model.addAttribute("errorMessage", model.getAttribute("errorMessage"));
                        }
                        return "assetss/form/orderDetail";

                    } catch (IllegalArgumentException e) {
                        model.addAttribute("errorMessage", e.getMessage());
                        model.addAttribute("noOrderDetailsMessage", "Đơn hàng này không có chi tiết nào.");
                        return "assetss/form/orderDetail";
                    } catch (Exception e) {
                        model.addAttribute("errorMessage", "Lỗi khi truy xuất chi tiết đơn hàng: " + e.getMessage());
                        return "assetss/form/orderDetail";
                    }
                }
            }
        }

        // Nếu người dùng chưa đăng nhập
        model.addAttribute("errorMessage", "Bạn cần đăng nhập để xem chi tiết đơn hàng.");
        return "redirect:/security/login/form";
    }

}
