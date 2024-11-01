package com.poly.datn.Service;

import com.poly.datn.Entity.Cart.CartDetail;

import java.util.List;
import java.util.Optional;

public interface CartDetailService {
    public CartDetail updateCartDetail(Long id, CartDetail cartDetailDetails);
    public CartDetail addProductToCart(Long userId, Long storeId,  int quantity);

//    void deleteById(Long cartDetailId);
    public List<CartDetail> getAllCartDetailsForCart(Long cartId);

    //    Thêm sản phẩm vào giỏ hàng
    //    đang lỗi khi chưa xong cart và user ( chưa lấy được 2 id )

}
