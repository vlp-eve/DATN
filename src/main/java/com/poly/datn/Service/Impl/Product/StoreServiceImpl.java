package com.poly.datn.Service.Impl.Product;

import com.poly.datn.Entity.DTO.StoreDTO;
import com.poly.datn.Entity.IsDelete;
import com.poly.datn.Entity.Product.Discount;
import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product;

import com.poly.datn.Entity.Product.Store;
import com.poly.datn.Repository.DiscountRepository;
import com.poly.datn.Repository.InventoryRepository;
import com.poly.datn.Repository.Product1Repository;
import com.poly.datn.Repository.StoreRepository;
import com.poly.datn.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;


    @Autowired
    private Product1Repository product1Repository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private DiscountRepository discountRepository;

    public List<Store> findAllStore(){
       return storeRepository.findAll();
    }



    public void setDiscount() {
        List<Product> product1List = product1Repository.findByIsDeletedFalse();
        for (Product product1 : product1List) {
            List<Inventory> inventories = inventoryRepository.findByProduct_Id(product1.getId());
            for (Inventory inventory : inventories) {
                Discount discount = null;

                // Xác định discount dựa trên status của inventory
                if (inventory.getStatus().getId() == 1 || inventory.getStatus().getId() == 4 || inventory.getStatus().getId() == 5|| inventory.getIsDeleted()== IsDelete.DELETED.getValue()) {
                    discount = null;
                } else if (inventory.getStatus().getId() == 2) {
                    discount = discountRepository.getReferenceById(1L);
                } else if (inventory.getStatus().getId() == 3) {
                    discount = discountRepository.getReferenceById(2L);
                } else {
                    System.out.println("Không có trạng thái phù hợp");
                }

                // Tìm kiếm store
                Store store = storeRepository.findByProduct_IdAndInventory_Id(
                        product1.getId(), inventory.getId());

                // Kiểm tra store có null không
                if (store != null) {
                    store.setDiscount(discount);
                    storeRepository.save(store);
                } else {
                    System.out.println("Không tìm thấy store cho productId: " + product1.getId() +
                            " và inventoryId: " + inventory.getId());
                }
            }
        }
    }


    public List<Object[]> getProductsWithUpcomingExpiry() {
        LocalDate currentDate = LocalDate.now();
        return storeRepository.findStoresWithValidInventory(currentDate);
    }

}
