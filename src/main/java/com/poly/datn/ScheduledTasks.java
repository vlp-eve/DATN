package com.poly.datn;

import com.poly.datn.Service.Impl.InventoryServiceImpl;
import com.poly.datn.Service.InventoryService;
import com.poly.datn.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private StoreService storeService;

    // Chạy hàng ngày vào lúc 0 giờ để kiểm tra và cập nhật trạng thái sản phẩm
    @Scheduled(cron = "0 0 * * * ?")
    public void updateInventoryAndDiscountsDaily() {
        inventoryService.updateInventoryStatusAndDiscount();
        storeService.setDiscount();
    }
}
