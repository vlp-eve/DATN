package com.poly.datn.Service;

import com.poly.datn.Entity.Cart.CartDetail;
import com.poly.datn.Entity.User.User;

import java.util.List;

public interface CartDetailService {
    public CartDetail updateCartDetail(Long id, CartDetail cartDetailDetails);
    public CartDetail addProductToCart(User user, Long storeId, Double quantity);

//    void deleteById(Long cartDetailId);
    public List<CartDetail> getAllCartDetailsForCart(Long cartId);

    //    Thêm sản phẩm vào giỏ hàng
    //    đang lỗi khi chưa xong cart và user ( chưa lấy được 2 id )
    void deleteCartDetail(Long cartId, Long cartDetailId);
}
