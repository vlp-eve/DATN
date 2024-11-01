package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.Cart.CartDetail;
import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Order.OrderDetail;
import com.poly.datn.Entity.StatusOrder;
import com.poly.datn.Entity.User.User;
import com.poly.datn.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class OrderDetailServiceImpl {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;


    public void createOrderFromCart(Long userId, String address) {
        // Tìm người dùng và giỏ hàng của họ
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.getByUser_Id(userId);

        // Tạo đối tượng Order mới
        Order order = new Order();
        order.setTotalAmount(cart.getTotalPrice());
        order.setCreateDate(LocalDate.now());
        order.setStatus(StatusOrder.PENDING);
        order.setAddress(address);
        order.setUser(user);
        order = orderRepository.save(order);

//        Thêm từ giỏ hàng vào đơn hàng
        List<CartDetail> cartDetailList = cartDetailRepository.findByCartId(cart.getId());
        for (CartDetail cartDetail : cartDetailList) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setPrice(cartDetail.getPrice());
            orderDetail.setUnit(cartDetail.getUnit());
            orderDetail.setStore(cartDetail.getStore());
            orderDetailRepository.save(orderDetail);

//            xóa sản phẩm trong giỏ hàng
            cartDetailRepository.delete(cartDetail);
        }


        // Xóa giỏ hàng sau khi tạo đơn hàng
        cartRepository.delete(cart);
    }

}
