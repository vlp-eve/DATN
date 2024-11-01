package com.poly.datn.Service.Impl.Product;

import com.poly.datn.Entity.IsDelete;
import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product;

import com.poly.datn.Entity.Product.Store;

import com.poly.datn.Repository.InventoryRepository;
import com.poly.datn.Repository.Product1Repository;
import com.poly.datn.Repository.StatusRepository;
import com.poly.datn.Repository.StoreRepository;
import com.poly.datn.Service.DiscountService;
import com.poly.datn.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InventoryServiceImpl implements InventoryService {
        @Autowired
        private InventoryRepository inventoryRepository;

        @Autowired
        private DiscountService discountService;

        @Autowired
        private StatusRepository statusRepository;
        private  LocalDate today = LocalDate.now();
        @Autowired
        private Product1Repository product1Repository;
        @Autowired
        private StoreRepository storeRepository;

        // lấy tất cả lô hàng chưa bị xóa
        public List<Inventory> getAllInventories() {
            return inventoryRepository.findByIsDeletedFalse();
        }
        // lấy lô hàng theo id
        public Inventory getInventoryById(Long inventoryId) {
            return inventoryRepository.findInventoryById(inventoryId);
        }

        //Lưu lô hàng mới
    public void saveInventory(Inventory inventory) {
        LocalDate expireDate = inventory.getExpiryDate();
        long daysUntilExpiry = expireDate.toEpochDay() - today.toEpochDay();

        // Thiết lập trạng thái cho inventory
        if (daysUntilExpiry <= 0) {
            inventory.setStatus(statusRepository.getReferenceById(1L));
        } else if (daysUntilExpiry <= 5) {
            inventory.setStatus(statusRepository.getReferenceById(2L));
        } else if (daysUntilExpiry <= 10) {
            inventory.setStatus(statusRepository.getReferenceById(3L));
        } else if (inventory.getQuantity() == 0) {
            inventory.setStatus(statusRepository.getReferenceById(5L));
        } else {
            inventory.setStatus(statusRepository.getReferenceById(4L));
        }

        inventory.setReceivedDate(today);

        // Lưu tổng sản phẩm
        Product product1 = product1Repository.findProductById(inventory.getProduct().getId());
        product1.setTotalQuantity(inventory.getQuantity() + product1.getTotalQuantity());
        product1Repository.save(product1);

        // Lưu inventory
        Inventory savedInventory = inventoryRepository.save(inventory);

        // Tạo và thiết lập Store
        Store store = new Store();

        // Tạo StoreId và thiết lập cho Store

        store.setProduct(product1);
        store.setInventory(savedInventory);

        // Thiết lập các thuộc tính khác

        store.setDiscount(null);

        // Lưu Store
        storeRepository.save(store);
        //khi viết controller nhớ thêm setdiscount bên store qua
    }


    // update inventory
        public void updateInvetory(Inventory inventory){
            LocalDate expireDate = inventory.getExpiryDate();
            long daysUntilExpiry = expireDate.toEpochDay() - today.toEpochDay();
            if (daysUntilExpiry <= 0) {
                inventory.setStatus(statusRepository.getReferenceById(1L));
            } else if (daysUntilExpiry <= 5) {
                inventory.setStatus(statusRepository.getReferenceById(2L));
            } else if (daysUntilExpiry <= 10) {
                inventory.setStatus(statusRepository.getReferenceById(3L));
            } else if( inventory.getQuantity() == 0){
                inventory.setStatus(statusRepository.getReferenceById(5L));
            }
            else {
                inventory.setStatus(statusRepository.getReferenceById(4L));
            }
            Product product1 = product1Repository.findProductById(inventory.getProduct().getId());
            product1.setTotalQuantity(inventory.getQuantity()+ product1.getTotalQuantity() );
            product1Repository.save(product1);
            inventoryRepository.save(inventory);
        }
        // bên controller nhớ set product trong invetory


        public void deleteInventory(Long inventoryId) {
          Inventory inventory = inventoryRepository.findInventoryById(inventoryId);
          if(inventory.getIsDeleted() == IsDelete.DELETED.getValue()){
          inventory.setIsDeleted(IsDelete.DELETED.getValue());
          Product product1 = product1Repository.findProductById(inventory.getProduct().getId());
          product1.setTotalQuantity( product1.getTotalQuantity()- inventory.getQuantity() );
          product1Repository.save(product1);
          inventoryRepository.save(inventory);
          }else {
              System.out.println("Có lỗi xảy ra");
          }
        }



// kiểm tra và update sản phẩm từ lô hàng
    public void updateInventoryStatusAndDiscount() {
        List<Inventory> inventories = inventoryRepository.findAll();
        LocalDate currentDate = LocalDate.now();

        // Đặt lại totalQuantity về 0 trước khi tính toán lại
        Map<Long, Integer> productQuantityMap = new HashMap<>();

        List<Product> products = product1Repository.findAll();
        for (Product product : products) {
            product.setTotalQuantity(0);
            product1Repository.save(product);
        }


        for (Inventory inventory : inventories) {
            // Tính toán số ngày còn lại đến ngày hết hạn
            long daysToExpiry = inventory.getExpiryDate().toEpochDay() - currentDate.toEpochDay();

            // Cập nhật trạng thái dựa trên thời gian còn lại đến ngày hết hạn
            if (daysToExpiry <= 0) {
                inventory.setStatus(statusRepository.getReferenceById(1L));
            } else if (daysToExpiry <= 5) {
                inventory.setStatus(statusRepository.getReferenceById(2L));
            } else if (daysToExpiry <= 10) {
                inventory.setStatus(statusRepository.getReferenceById(3L));
            } else if (inventory.getQuantity() == 0) {
                inventory.setStatus(statusRepository.getReferenceById(5L));
            } else {
                inventory.setStatus(statusRepository.getReferenceById(4L));
            }


            // Tính toán lại totalQuantity cho từng sản phẩm
            if (inventory.getStatus().getId() != 1 && inventory.getIsDeleted()== IsDelete.ACTIVE.getValue() ) {
                Long productId = inventory.getProduct().getId();
                productQuantityMap.put(productId,
                        productQuantityMap.getOrDefault(productId, 0) + inventory.getQuantity());
            }

            inventoryRepository.save(inventory);
        }

        // Cập nhật totalQuantity cho các sản phẩm
        for (Map.Entry<Long, Integer> entry : productQuantityMap.entrySet()) {
            Product product1 = product1Repository.findProductById(entry.getKey());
            product1.setTotalQuantity(entry.getValue());
            product1Repository.save(product1);
        }
    }




    //  xử lý buôn bán
//    public void processNearestExpiryInventory(Long productId, int quantity) {
//        List<Inventory> availableInventories = inventoryRepository.findAvailableInventories(productId, LocalDate.now());
//        int totalAvailableQuantity = availableInventories.stream()
//                .mapToInt(Inventory::getQuantity)
//                .sum();
//
//
//        int remainingQuantity = quantity;
//
//        for (Inventory inventory : availableInventories) {
//            if (totalAvailableQuantity < quantity) {
//                System.out.println("Không đủ hàng trong tất cả các lô hàng gần hết hạn.");
//                return;
//            }
//            if (inventory.getQuantity() >= remainingQuantity) {
//                inventory.setQuantity(inventory.getQuantity() - remainingQuantity);
//                inventoryRepository.save(inventory);
//                System.out.println("Đã xử lý " + quantity + " sản phẩm từ lô hàng gần hết hạn.");
//                Product1 product1 = product1Repository.findProductById(inventory.getProduct().getProductId());
//                product1.setTotalQuantity(inventory.getQuantity()+ product1.getTotalQuantity() );
//                product1Repository.save(product1);
//                return;
//            } else {
//                remainingQuantity -= inventory.getQuantity();
//                inventory.setQuantity(0);
//                inventoryRepository.save(inventory);
//            }
//        }
//    }
}


