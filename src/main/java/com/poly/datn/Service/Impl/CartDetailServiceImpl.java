package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.Cart.CartDetail;
import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Store;
import com.poly.datn.Repository.*;
import com.poly.datn.Service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public List<CartDetail> getAllCartDetail() {
        return cartDetailRepository.findAll();
    }

    public CartDetail saveCartDetail(CartDetail cartDetail) {
        return cartDetailRepository.save(cartDetail);
    }

    public List<CartDetail> getAllCartDetailsForCart(Long cartId) {
        return cartDetailRepository.findByCartId(cartId);
    }

    public Optional<CartDetail> getCartDetailById(Long id) {
        return cartDetailRepository.findById(id);
    }

    public void deleteCartDetail(Long id) {
        cartDetailRepository.deleteById(id);
    }

//    đang chỉnh lại
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
    public CartDetail addProductToCart( Long userId, Long productId, int quantity) {
        LocalDate today = LocalDate.now();
        // Tìm sản phẩm trong cửa hàng


        // Tìm giỏ hàng
        Cart cart = cartRepository.getByUser_Id(userId);

        // nếu giỏ hàng chưa có sẽ tự tạo cho người dùng
        if (cart == null){
            Cart cart1 = new Cart();
            cart1.setCreateDate(today);
            cart1.setUser(userRepository.getById(userId));
            cart1.setTotalPrice(0.0);
            cart = cartRepository.save(cart1);
        }

        List<Inventory> inventories = inventoryRepository.findByProduct_Id(productId);
        Store store = null;

        for (Inventory inventory : inventories) {
            if (inventory.getQuantity() >= quantity) {
                store = storeRepository.findByProduct_IdAndInventory_Id(productId, inventory.getId());
                break;
            }
        }

        if (store == null) {
            System.out.println("Không đủ số lượng");
            return null;
        }

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Optional<CartDetail> existingCartDetail = cartDetailRepository.findByCart_IdAndStore_Id(cart.getId(), store.getId());


        Double price = product1Repository.findProductById(productId).getPrice();
        CartDetail cartDetail;
        if (existingCartDetail.isPresent()) {
            // Nếu sản phẩm đã có, cập nhật số lượng và giá
            cartDetail = existingCartDetail.get();
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
            cartDetail.setUnit(store.getProduct().getUnit());
            cartDetail.setPrice(cartDetail.getPrice() + price);
        } else {
            // Nếu sản phẩm chưa có, thêm sản phẩm mới vào giỏ hàng
            cartDetail = new CartDetail();
            cartDetail.setCart(cart);
            cartDetail.setStore(store);
            cartDetail.setQuantity(quantity);
            cartDetail.setUnit(store.getProduct().getUnit());
            cartDetail.setPrice(price);
        }

        // Cập nhật lại tổng giá trong giỏ hàng
        cart.setTotalPrice(cart.getTotalPrice() + price * quantity);
        cartRepository.save(cart);

        return cartDetailRepository.save(cartDetail);
    }

}



