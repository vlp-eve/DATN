package com.poly.datn.Controller.User;


import com.poly.datn.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

//    @GetMapping("/valid-inventory")
//    public String getStoresWithValidInventory(Model model) {
//        // Gọi service để lấy dữ liệu các sản phẩm có sắp hết hạn
//        List<Object[]> results = storeService.getProductsWithUpcomingExpiry();
//        List<StoreDTO> storeResponses = new ArrayList<>();
//
//
//        for (Object[] result : results) {
//            Long storeId = (Long) result[0];
//            Discount discount = (Discount) result[1];
//            Product product = (Product) result[2];
//            Inventory inventory = (Inventory) result[3];
//
//
//            storeResponses.add(new StoreDTO(storeId, discount, product, inventory));
//        }
//
//        // Thêm dữ liệu vào model để truyền đến giao diện
//        model.addAttribute("stores", storeResponses);
//        return "assetss/form/store";
//    }


}
