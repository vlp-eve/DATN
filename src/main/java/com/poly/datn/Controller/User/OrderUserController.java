package com.poly.datn.Controller.User;


import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Payment.Method;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ShippingService shippingService;

    @Transactional
    @PostMapping("/confirm")
    public String confirmOrder(
            @RequestParam String customerPhone,
            @RequestParam String customerAddress,
            @RequestParam(required = false) String note,
            @RequestParam Long paymentMethod,
            Model model) {

        System.out.println("customerPhone: " + customerPhone);
        System.out.println("customerAddress: " + customerAddress);
        System.out.println("note: " + note);
        System.out.println("paymentMethod: " + paymentMethod);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userService.findByUsernameUser(username);

                if (user != null) {
                    try {
                        // Tạo đơn hàng từ giỏ hàng và lấy đối tượng Order
                        Order order = orderDetailService.createOrderFromCart(user.getId(), paymentMethod);

                        //  Thêm thông tin vận chuyển và phương thức thanh toán
                        shippingService.addShippingAndMethodPay(order.getId(), customerPhone, customerAddress, note, paymentMethod);

                        // Thêm thông báo thành công
                        model.addAttribute("message", "Đơn hàng đã được xác nhận thành công!");
                        return "redirect:/order/" + order.getId() + "/qr-payment";

                    } catch (RuntimeException e) {
                        model.addAttribute("message", "Có lỗi xảy ra: " + e.getMessage());
                        System.out.println(e.getMessage());
                        return "assetss/form/checkout";  // Hiển thị lại trang checkout với thông báo lỗi
                    }
                }
            }
        }

        // Nếu người dùng chưa đăng nhập
        return "redirect:/security/login/form";  // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
    }
    @GetMapping("/{orderId}/qr-payment")
    public String showQRPaymentPage(@PathVariable Long orderId, Model model) {

        Order order = orderService.getOrderById(orderId);
        Method paymentMethod = order.getMethod();
        model.addAttribute("orderId", orderId);
        model.addAttribute("paymentMethod", paymentMethod);
        model.addAttribute("css","/assetss/css/payment.css");
        return "assetss/form/payment";
    }

    @Transactional
    @PostMapping("/{orderId}/process-payment")
    public String processOrderPayment(@PathVariable Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderDetailService.processOrderPayment(orderId);
            redirectAttributes.addFlashAttribute("successMessage", "Thanh toán thành công cho đơn hàng #" + orderId);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        return "redirect:/order/" + orderId + "/order-success"; // Quay lại trang danh sách đơn hàng
    }

    @GetMapping("/{orderId}/order-success")
    public String showOrderSuccessPage(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);
        model.addAttribute("css","/assetss/css/checkoutOk.css" );
        return "assetss/form/checkoutOk";
    }

    @GetMapping("/user/orders")
    public String getAllOrdersByAuthenticatedUser(Model model) {
        model.addAttribute("css", "/assetss/css/orders.css");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userService.findByUsernameUser(username);

                if (user != null) {
                    try {
                        // Lấy danh sách đơn hàng của người dùng
                        List<Order> orders = orderService.getOrderByUserId(user.getId());

                        // Kiểm tra nếu danh sách đơn hàng trống
                        if (orders.isEmpty()) {
                            model.addAttribute("noOrdersMessage", "Bạn hiện chưa có đơn hàng nào, hãy nhanh tay đặt hàng nào!");
                        }

                        // Thêm danh sách đơn hàng vào model
                        model.addAttribute("orders", orders);
                        return "assetss/form/order";

                    } catch (IllegalArgumentException e) {
                        model.addAttribute("errorMessage", e.getMessage());
                        model.addAttribute("noOrdersMessage", "Bạn hiện chưa có đơn hàng nào, hãy nhanh tay đặt hàng nào!");

                        return "assetss/form/order";
                    } catch (Exception e) {
                        model.addAttribute("errorMessage", "Lỗi khi truy xuất đơn hàng: " + e.getMessage());
                        return "assetss/form/order";
                    }
                }
            }
        }

        // Nếu người dùng chưa đăng nhập
        model.addAttribute("errorMessage", "Bạn cần đăng nhập để xem danh sách đơn hàng.");
        return "redirect:/security/login/form";
    }

    @PostMapping("/user/orders/cancel")
    public String cancelOrder(@RequestParam("orderId") Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.canceledOrder(orderId);
            redirectAttributes.addFlashAttribute("successMessage", "Hủy đơn hàng thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/orderDetail/" + orderId;
    }


}