package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.Cart.CartDetail;
import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Order.OrderDetail;
import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.StatusOrder;
import com.poly.datn.Entity.User.User;
import com.poly.datn.Repository.*;
import com.poly.datn.Service.InventoryService;
import com.poly.datn.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {


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

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private Product1Repository product1Repository;

//    tạo order từ cart
    public void createOrderFromCart(Long userId, String address) {
        try {
            // Tìm người dùng và giỏ hàng của họ
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Cart cart = cartRepository.getByUser_Id(userId);

            if (cart == null) {
                throw new RuntimeException("Cart not found for user");
            }

            // Tạo đối tượng Order mới
            Order order = new Order();
            order.setTotalAmount(cart.getTotalPrice());
            order.setCreateDate(LocalDate.now());
            order.setStatus(StatusOrder.PENDING);
            order.setAddress(address);
            order.setUser(user);
            order = orderRepository.save(order);

    //            Tạo list sản phẩm kh đủ số lượng
            List<String> insufficientProduct = new ArrayList<>();

            // Thêm từ giỏ hàng vào đơn hàng
            List<CartDetail> cartDetailList = cartDetailRepository.findByCartId(cart.getId());
            for (CartDetail cartDetail : cartDetailList) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                if(cartDetail.getQuantity()< cartDetail.getStore().getInventory().getQuantity()){
                    insufficientProduct.add(cartDetail.getStore().getProduct().getName());
                    continue;
                }else {
                orderDetail.setQuantity(cartDetail.getQuantity());
//                xóa số lượng trong lô hàng
                deleteQuantityWhenOrder(cartDetail);
                orderDetail.setPrice(cartDetail.getPrice());
                orderDetail.setUnit(cartDetail.getUnit());
                orderDetail.setStore(cartDetail.getStore());
                orderDetailRepository.save(orderDetail);
                // Xóa sản phẩm trong giỏ hàng
                cartDetailRepository.delete(cartDetail);
                }
            }
//            update lại tổng quantity và status của từng lô hàng
            inventoryService.updateInventoryStatusAndDiscount();
            // Xóa giỏ hàng sau khi tạo đơn hàng
            cartRepository.delete(cart);
        if (!insufficientProduct.isEmpty() && insufficientProduct != null){
            throw new RuntimeException("Không đủ số lượng ở các mặt hàng: "+String.join(",", insufficientProduct));
        }
        }catch (IllegalArgumentException e){
            throw new RuntimeException(e);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Có lỗi khi tạo Order detail: " + e.getMessage(), e);
        }
    }



//     tìm orderdetail dựa vào orderid
    public List<OrderDetail> getOrderDetailByOrderId(Long orderId){
        return orderDetailRepository.findByOrder_Id(orderId);
    }

    public void deleteQuantityWhenOrder(CartDetail cartDetail){
        try {
            Inventory inventory = inventoryRepository.findInventoryById(cartDetail.getStore().getInventory().getId());
            inventory.setQuantity(inventory.getQuantity() - cartDetail.getQuantity());
            inventoryRepository.save(inventory);
        }catch (RuntimeException e){
            throw new RuntimeException("lỗi khi trừ số lượng: "+e );
        }
    }
}
