package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Order.Order;
import com.poly.datn.Entity.Order.OrderDetail;

import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.StatusOrder;
import com.poly.datn.Repository.InventoryRepository;
import com.poly.datn.Repository.OrderDetailRepository;
import com.poly.datn.Repository.OrderRepository;
import com.poly.datn.Service.OrderService;
import jakarta.transaction.Transactional;
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

    @Autowired
    private InventoryRepository inventoryRepository;



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
//    mua lại từ đơn hàng cũ
//    thêm xử lí khi các trạng thái
@Transactional
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
                orderDetailNew.setStore(orderDetail.getStore());
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
//Thêm cộng lại số lượng


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

                // Đặt trạng thái đơn hàng là CANCELED
                order.setStatus(StatusOrder.CANCELED);
                orderRepository.save(order);

                // Lấy tất cả OrderDetail của đơn hàng để hoàn lại số lượng
                List<OrderDetail> orderDetails = orderDetailRepository.findByOrder_Id(orderId);
                for (OrderDetail orderDetail : orderDetails) {
                    Inventory inventory = orderDetail.getStore().getInventory();
                    inventory.setQuantity(inventory.getQuantity() + orderDetail.getQuantity());
                    inventoryRepository.save(inventory); // Cập nhật lại số lượng trong kho
                    orderDetailRepository.delete(orderDetail);
                }
                System.out.println("Đơn hàng đã được hủy và số lượng đã hoàn lại.");
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
