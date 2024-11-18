package com.poly.datn.Controller.Admin;


import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    public String test(Model model){
        List<Order> orderList = orderService.getAllOrder();
        model.addAttribute("orders", orderList);
        model.addAttribute("css","/assets/css/qlOrder.css");
        return "assets/form/listOrder";
    }

}
