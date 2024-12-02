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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Autowired
    private MethodRepository methodRepository;


//    tạo order từ cart

    @Transactional
    public Order createOrderFromCart(Long userId, Long methodId) {
        Order order = null;  // Khởi tạo đối tượng Order
        // Bắt đầu khối try-catch để xử lý các ngoại lệ
        try {
            // Lấy thông tin người dùng và giỏ hàng
            User user = userRepository.findUserById(userId);
            Cart cart = cartRepository.getByUser_Id(userId);
            if (cart == null) {
                throw new RuntimeException("Cart not found for user");
            }
            // Khởi tạo đơn hàng mới
            order = new Order();
            order.setTotalAmount(cart.getTotalPrice());
            order.setCreateDate(LocalDate.now());
            order.setStatus(StatusOrder.WAITPAY);
            order.setUser(user);
            order.setMethod(methodRepository.findMethodById(methodId));
            order = orderRepository.save(order);
            // Danh sách sản phẩm không đủ số lượng
            List<String> insufficientProduct = new ArrayList<>();
            // Lấy danh sách chi tiết giỏ hàng
            List<CartDetail> cartDetailList = cartDetailRepository.findByCartId(cart.getId());

            // Duyệt qua từng chi tiết giỏ hàng
            for (CartDetail cartDetail : cartDetailList) {
                Inventory inventory = inventoryRepository.findInventoryById(cartDetail.getStore().getInventory().getId());

                // Kiểm tra số lượng tồn kho
                if (cartDetail.getQuantity() > inventory.getQuantity()) {
                    insufficientProduct.add(cartDetail.getStore().getProduct().getName());
                    continue;
                }
                // Tạo chi tiết đơn hàng
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setQuantity(cartDetail.getQuantity());
                orderDetail.setPrice(cartDetail.getPrice());
                orderDetail.setUnit(cartDetail.getUnit());
                orderDetail.setStore(cartDetail.getStore());

                // Giảm số lượng tồn kho
//                inventory.setQuantity(inventory.getQuantity() - cartDetail.getQuantity());
//                inventoryRepository.save(inventory);

                // Lưu chi tiết đơn hàng
                orderDetailRepository.save(orderDetail);
            }
            // Cập nhật trạng thái kho
            inventoryService.updateInventoryStatusAndDiscount();

            // Xóa từng chi tiết giỏ hàng sau khi đã chuyển thành đơn hàng
            for (CartDetail cartDetail : cartDetailList) {
                cartDetail = cartDetailRepository.findById(cartDetail.getId()).orElseThrow(() -> new RuntimeException("CartDetail not found"));
                cartDetailRepository.delete(cartDetail);  // Xóa chi tiết giỏ hàng
            }
            // Xóa giỏ hàng sau khi tạo đơn hàng
            cart = cartRepository.findById(cart.getId()).orElseThrow(() -> new RuntimeException("Cart not found"));
            cartRepository.delete(cart);  // Xóa giỏ hàng
            // Kiểm tra nếu có sản phẩm không đủ số lượng
            if (!insufficientProduct.isEmpty()) {
                throw new RuntimeException("Không đủ số lượng ở các mặt hàng: " + String.join(", ", insufficientProduct));
            }
        } catch (Exception e) {
            // Ghi log lỗi và thông báo khi giao dịch thất bại
            throw new RuntimeException("Error during transaction: " + e.getMessage(), e);
        }
        // Trả về đối tượng Order đã được tạo
        return order;
    }


    @Transactional
    public void processOrderPayment(Long orderId) {
        try {
            // Lấy đơn hàng
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found"));

            // Kiểm tra trạng thái, chỉ xử lý khi đơn hàng đang chờ thanh toán
            if (order.getStatus() != StatusOrder.WAITPAY) {
                throw new RuntimeException("Order is not in waiting payment status");
            }

            // Duyệt qua chi tiết đơn hàng
            List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_Id(orderId);
            for (OrderDetail orderDetail : orderDetails) {
                Inventory inventory = inventoryRepository.findById(orderDetail.getStore().getInventory().getId())
                        .orElseThrow(() -> new RuntimeException("Inventory not found"));

                // Trừ số lượng tồn kho
                if (orderDetail.getQuantity() > inventory.getQuantity()) {
                    throw new RuntimeException("Not enough inventory for product: " + orderDetail.getStore().getProduct().getName());
                }
                inventory.setQuantity(inventory.getQuantity() - orderDetail.getQuantity());
                inventoryRepository.save(inventory);
                inventoryService.updateInventoryStatusAndDiscount();
            }

            // Cập nhật trạng thái đơn hàng thành đã thanh toán
            order.setStatus(StatusOrder.PENDING);
            orderRepository.save(order);

        } catch (Exception e) {
            throw new RuntimeException("Error during payment processing: " + e.getMessage(), e);
        }
    }

//     tìm orderdetail dựa vào orderid
    public List<OrderDetail> getOrderDetailByOrderId(Long orderId) {
        try {
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder_Id(orderId);
            if (orderDetailList == null || orderDetailList.isEmpty()) {
                throw new NoSuchElementException("Không tìm thấy chi tiết đơn hàng cho Order ID: " + orderId);
            }
            return orderDetailList;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Đã xảy ra lỗi khi truy xuất chi tiết đơn hàng", e);
        }
    }


    public void deleteQuantityWhenOrder(CartDetail cartDetail) {
        try {
            // Lấy Inventory từ CartDetail
            Inventory inventory = inventoryRepository.findInventoryById(cartDetail.getStore().getInventory().getId());


            // Kiểm tra số lượng trước khi trừ
            if (inventory.getQuantity() < cartDetail.getQuantity()) {
                throw new RuntimeException("Không đủ số lượng trong kho để trừ. Hiện tại: "
                        + inventory.getQuantity() + ", yêu cầu: " + cartDetail.getQuantity());

            }

            // Trừ số lượng tồn kho
            inventory.setQuantity(inventory.getQuantity() - cartDetail.getQuantity());

            // Lưu Inventory sau khi cập nhật
            inventoryRepository.save(inventory);
        } catch (RuntimeException e) {

            throw new RuntimeException("Lỗi khi trừ số lượng: " + e.getMessage(), e);

        } catch (Exception e) {

            throw new RuntimeException("Có lỗi không mong muốn khi trừ số lượng: " + e.getMessage(), e);
        }
    }

}
