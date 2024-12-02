package com.poly.datn.Controller.Admin;


import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.StatusOrder;
import com.poly.datn.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public String test(Model model){
        List<Order> orderList = orderService.getAllOrder();
        model.addAttribute("orders", orderList);
        model.addAttribute("css","/assets/css/qlOrder.css");
        return "assets/form/listOrder";
    }
    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam("orderId") Long orderId, RedirectAttributes redirectAttributes) {
        try {
            orderService.canceledOrder(orderId);
            redirectAttributes.addFlashAttribute("successMessage", "Hủy đơn hàng thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/admin/orders";
    }

    @PostMapping("/updateStatus")
    public String updateOrderStatus(@RequestParam("id") Long orderId, @RequestParam("status") StatusOrder status, RedirectAttributes redirectAttributes) {
        // Lấy đơn hàng theo ID
        try {
            Order order = orderService.getOrderById(orderId);

            if (order != null) {
                order.setStatus(status);
                orderService.save(order);
                redirectAttributes.addFlashAttribute("successMessage", "Sửa trạng thái đơn hàng thành công!");
            }


            return "redirect:/admin/orders";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sửa trạng thái đơn hàng thất bại. Vui lòng thử lại.");
            return "redirect:/admin/orders";

        }
    }
}

