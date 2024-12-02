package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.Cart.CartDetail;
import com.poly.datn.Entity.Product.Store;
import com.poly.datn.Entity.User.User;
import com.poly.datn.Repository.*;
import com.poly.datn.Service.CartDetailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private  Product1Repository product1Repository;
    public List<CartDetail> getAllCartDetailsForCart(Long cartId) {
        return cartDetailRepository.findByCartId(cartId);
    }






    public CartDetail updateCartDetail(Long id, CartDetail cartDetailDetails) {
        Optional<CartDetail> optionalCartDetail = cartDetailRepository.findById(id);
        if (optionalCartDetail.isPresent()) {
            CartDetail existingCartDetail = optionalCartDetail.get();
            existingCartDetail.setQuantity(cartDetailDetails.getQuantity());
            existingCartDetail.setPrice(cartDetailDetails.getPrice());
            return cartDetailRepository.save(existingCartDetail);
        } else {
            throw new RuntimeException("Không thấy Sản phẩm: " + id);
        }
    }


//    Thêm sản phẩm vào giỏ hàng



    @Override
    public CartDetail addProductToCart(User user, Long storeId, Double quantity) {
        LocalDate today = LocalDate.now();
        try {
            // Tìm giỏ hàng
            Cart cart = cartRepository.getByUser_Id(user.getId());

            // Nếu giỏ hàng chưa có, tạo mới
            if (cart == null) {
                cart = new Cart();
                cart.setCreateDate(today);
                cart.setUser(user);
                cart.setTotalPrice(0.0);
                cart = cartRepository.save(cart);
            }

            // Lấy thông tin sản phẩm từ Store
            Store store = storeRepository.getReferenceById(storeId);
            if (store == null) {
                throw new RuntimeException("Không tìm thấy sản phẩm");
            }

            // Kiểm tra số lượng tồn kho
            if (store.getInventory().getQuantity() < quantity) {
                throw new RuntimeException("Không đủ số lượng trong kho");
            }

            // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
            Optional<CartDetail> existingCartDetail = cartDetailRepository.findByCart_IdAndStore_Id(cart.getId(), store.getId());

            // Lấy giá sản phẩm
            Double price = product1Repository.findProductById(store.getProduct().getId()).getPrice();
            Double discountPrice = price; // Giá sau khi áp dụng giảm giá

            // Áp dụng giảm giá nếu có
            if (store.getDiscount() != null) {
                discountPrice = price - (price * store.getDiscount().getDiscountPercentage() / 100);
            }

            CartDetail cartDetail;

            if (existingCartDetail.isPresent()) {
                // Nếu sản phẩm đã có, cập nhật số lượng và giá
                cartDetail = existingCartDetail.get();
                cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
                cartDetail.setPrice(discountPrice);
            } else {
                // Nếu sản phẩm chưa có, thêm mới vào giỏ hàng
                cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setStore(store);
                cartDetail.setQuantity(quantity);
                cartDetail.setPrice(discountPrice);
                cartDetail.setUnit(store.getProduct().getUnit());
            }

            // Cập nhật lại tổng giá trong giỏ hàng
            Double totalToAdd = discountPrice * quantity;
            cart.setTotalPrice(cart.getTotalPrice() + totalToAdd);


            cartRepository.save(cart);
            return cartDetailRepository.save(cartDetail);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Không tìm thấy đối tượng: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    @Override
    public void deleteCartDetail(Long cartId, Long cartDetailId) {
        // Tìm CartDetail theo cartId và cartDetailId
        CartDetail cartDetail = cartDetailRepository.findByCart_IdAndId(cartId, cartDetailId);

        if (cartDetail != null) {
            // Trước khi xóa, trừ giá trị của sản phẩm bị xóa khỏi tổng giá trong giỏ hàng
            Cart cart = cartDetail.getCart();
            Double totalPriceToRemove = cartDetail.getPrice() * cartDetail.getQuantity();
            cart.setTotalPrice(cart.getTotalPrice() - totalPriceToRemove);

            // Lưu lại giỏ hàng sau khi đã cập nhật tổng giá trị
            cartRepository.save(cart);

            // Xóa CartDetail
            cartDetailRepository.delete(cartDetail);
        } else {
            throw new EntityNotFoundException("Không tìm thấy sản phẩm trong giỏ hàng.");
        }
    }



}



