package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Order.OrderDetail;

import com.poly.datn.Entity.StatusOrder;
import com.poly.datn.Repository.OrderDetailRepository;
import com.poly.datn.Repository.OrderRepository;
import com.poly.datn.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {



    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;




    public List<Order> getAllOrder(){
        try {
            return orderRepository.findAll();
        }catch (Exception e){
            throw  new RuntimeException("lỗi khi tìm tất cả đơn hàng");
        }
    }

    public Order getOrderById(Long orderId){
        try {
            return orderRepository.getOrderById(orderId);
        }catch (Exception e){
            throw  new RuntimeException("lỗi khi tìm đơn hàng bằng id");
        }

    }

//    Lấy đơn hàng của người dùng
    public List<Order> getOrderByUserId(Long userId) {
        try {
            if (userId == null) {
                throw new IllegalArgumentException("User ID không được để trống");
            }
            List<Order> orderListByUser = orderRepository.getOrderByUser_Id(userId);
            if (orderListByUser.isEmpty()) {
                throw new IllegalArgumentException("Không có đơn hàng nào cho người dùng có ID: " + userId);
            }
            return orderListByUser;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi truy xuất đơn hàng: " + e.getMessage(), e);
        }
    }
// mua lại từ đơn hàng cũ
    public void resetOldOrder(Long orderId, String address){
        try {
            if (orderId == null ){
                throw new IllegalArgumentException("OrderId không được để trống");
            }
            Order orderOld = orderRepository.getOrderById(orderId);
            Order order = new Order();
            order.setTotalAmount(orderOld.getTotalAmount());
            order.setUser(orderOld.getUser());
            if (address !=null && !address.isBlank()){
                order.setAddress(address);
            }else {
                order.setAddress(orderOld.getAddress());
            }
            order.setStatus(StatusOrder.PENDING);
            order.setCreateDate(LocalDate.now());

            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder_Id(orderId);
            for (OrderDetail orderDetail:orderDetailList){
                OrderDetail orderDetailNew = new OrderDetail();
                orderDetailNew.setPrice(orderDetail.getPrice());
                orderDetailNew.setUnit(orderDetail.getUnit());
                orderDetailNew.setQuantity(orderDetail.getQuantity());
                orderDetailNew.setOrder(order);
                orderDetailRepository.save(orderDetailNew);
            }
        }catch (IllegalArgumentException e){
            throw  e;
        }catch (Exception e){
            throw new RuntimeException("Có lỗi khi tạo Order detail: " + e.getMessage(), e);
        }
    }

//Hủy đơn hàng
public void canceledOrder(Long orderId) {
    try {
        Order order = getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId);
        }
        String status = order.getStatus().name();
        if (status.equals(StatusOrder.PENDING.name()) ||
                status.equals(StatusOrder.CONFIRMED.name()) ||
                status.equals(StatusOrder.PICKING.name())) {

            order.setStatus(StatusOrder.CANCELED);
            orderRepository.save(order);
            System.out.println("Đơn hàng đã được hủy thành công.");
        } else {
            throw new RuntimeException("Không thể hủy đơn hàng với trạng thái hiện tại: " + status);
        }
    } catch (Exception e) {
        throw new RuntimeException("Hủy đơn hàng thất bại: " + e.getMessage(), e);
    }
}


    // Xác nhận đơn hàng
    public void confirmedOrder(Long orderId) {
        try {
            Order order = getOrderById(orderId);
            if (order == null) {
                throw new RuntimeException("Không tìm thấy đơn hàng với ID: " + orderId);
            }
            // Kiểm tra trạng thái của đơn hàng
            String status = order.getStatus().name();
            if (status.equals(StatusOrder.PENDING.name())) {
                order.setStatus(StatusOrder.CONFIRMED);
                orderRepository.save(order);
            } else {
                throw new RuntimeException("Đơn hàng không ở trạng thái chờ xác nhận.");
            }
        } catch (Exception e) {

            throw new RuntimeException("Xác nhận đơn hàng thất bại: " + e.getMessage(), e);
        }
    }

}
