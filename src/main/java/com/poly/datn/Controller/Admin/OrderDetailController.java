package com.poly.datn.Controller.Admin;

import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Order.OrderDetail;
import com.poly.datn.Service.OrderDetailService;
import com.poly.datn.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderService orderService;  // Dịch vụ để lấy thông tin đơn hàng

    @GetMapping("/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model) {
        // Lấy chi tiết đơn hàng
        List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByOrderId(id);

        // Lấy thông tin đơn hàng (shipping, tổng tiền, v.v.)
        Order order = orderService.getOrderById(id);

        model.addAttribute("css", "/assets/css/qlOrderDetail.css");


        if (order != null && !orderDetails.isEmpty()) {
            model.addAttribute("order", order);
            model.addAttribute("orderDetails", orderDetails);
            return "assets/form/listOrderDetail";
        }

        // Nếu không tìm thấy đơn hàng


        return "error";
    }
}
