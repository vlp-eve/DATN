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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class InventoryServiceImpl implements InventoryService {
        @Autowired
        private InventoryRepository inventoryRepository;

        @Autowired
        private DiscountService discountService;

        @Autowired
        private StatusRepository statusRepository;

        @Autowired
        private Product1Repository product1Repository;
        @Autowired
        private StoreRepository storeRepository;

        private  LocalDate today = LocalDate.now();

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
        try {
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
            store.setProduct(product1);
            store.setInventory(savedInventory);
            store.setDiscount(null);

            // Lưu Store
            storeRepository.save(store);
        } catch (NullPointerException e) {
            throw new RuntimeException("Lỗi: có null trong quá trình xử lý", e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Lỗi truy xuất cơ sở dữ liệu", e);
        } catch (Exception e) {
            throw new RuntimeException("Đã xảy ra lỗi không xác định", e);
        }
//    khi viết controller nhớ thêm setdiscount từ store qua
    }




    // update inventory
    public void updateInventory(Inventory inventory) {
        try {
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

            // Cập nhật tổng số lượng sản phẩm
            Product product1 = product1Repository.findProductById(inventory.getProduct().getId());
            if (product1 == null) {
                throw new NoSuchElementException("Không tìm thấy sản phẩm với ID: " + inventory.getProduct().getId());
            }

            product1.setTotalQuantity(inventory.getQuantity() + product1.getTotalQuantity());
            product1Repository.save(product1);

            // Lưu inventory
            inventoryRepository.save(inventory);

        } catch (NullPointerException e) {
            throw new RuntimeException("có null trong quá trình xử lý", e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Lỗi truy xuất cơ sở dữ liệu", e);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Đã xảy ra lỗi không xác định", e);
        }
    }



    // bên controller nhớ set product trong invetory


    public void deleteInventory(Long inventoryId) {
        try {
            Inventory inventory = inventoryRepository.findInventoryById(inventoryId);

            if (inventory == null) {
                throw new NoSuchElementException("Không tìm thấy Inventory với ID: " + inventoryId);
            }

            if (inventory.getIsDeleted() == IsDelete.ACTIVE.getValue()) {
                inventory.setIsDeleted(IsDelete.DELETED.getValue());

                Product product1 = product1Repository.findProductById(inventory.getProduct().getId());
                if (product1 == null) {
                    throw new NoSuchElementException("Không tìm thấy sản phẩm với ID: " + inventory.getProduct().getId());
                }

                product1.setTotalQuantity(product1.getTotalQuantity() - inventory.getQuantity());
                product1Repository.save(product1);

                inventoryRepository.save(inventory);
            } else {
                System.out.println("Inventory đã bị xóa trước đó.");
            }

        } catch (NoSuchElementException e) {
            throw new RuntimeException("Lỗi: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Lỗi truy xuất cơ sở dữ liệu", e);
        } catch (Exception e) {
            throw new RuntimeException("Đã xảy ra lỗi không xác định", e);
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





}


