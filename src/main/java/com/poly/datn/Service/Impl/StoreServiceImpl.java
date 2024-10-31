package com.poly.datn.Service.Impl;

import com.poly.datn.Entity.DTO.Product1Dto;
import com.poly.datn.Entity.Product.Discount;
import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product1;
import com.poly.datn.Entity.Product.Store;
import com.poly.datn.Repository.DiscountRepository;
import com.poly.datn.Repository.InventoryRepository;
import com.poly.datn.Repository.Product1Repository;
import com.poly.datn.Repository.StoreRepository;
import com.poly.datn.Service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    @Override
    public List<Product1Dto> findAvailableProductsFromStore() {
//        return storeRepository.findAvailableProductsFromStore();
        List<Object[]> results = storeRepository.findAvailableProductsFromStore();
        return storeRepository.mapToProduct1Dtos(results);
    }


    public void setDiscount() {
        List<Product1> product1List = product1Repository.findByIsDeleteFalse();
        for (Product1 product1 : product1List) {
            List<Inventory> inventories = inventoryRepository.findByProduct_ProductId(product1.getProductId());
            for (Inventory inventory : inventories) {
                Discount discount = null;

                // Xác định discount dựa trên status của inventory
                if (inventory.getStatus().getId() == 1 || inventory.getStatus().getId() == 4 || inventory.getStatus().getId() == 5|| inventory.isDelete()==true) {
                    discount = null;
                } else if (inventory.getStatus().getId() == 2) {
                    discount = discountRepository.getReferenceById(1L);
                } else if (inventory.getStatus().getId() == 3) {
                    discount = discountRepository.getReferenceById(2L);
                } else {
                    System.out.println("Không có trạng thái phù hợp");
                }

                // Tìm kiếm store
                Store store = storeRepository.findByProduct_ProductIdAndInventory_InventoryId(
                        product1.getProductId(), inventory.getInventoryId());

                // Kiểm tra store có null không
                if (store != null) {
                    store.setDiscount(discount);
                    storeRepository.save(store);
                } else {
                    System.out.println("Không tìm thấy store cho productId: " + product1.getProductId() +
                            " và inventoryId: " + inventory.getInventoryId());
                }
            }
        }
    }


    @Override
    public List<Store> getProductsWithUpcomingExpiry() {
        LocalDate currentDate = LocalDate.now();
        return storeRepository.findTopByEachProductAndUpcomingExpiry(currentDate);
    }

//    public void test (){
//        List<Store> stores = findAllStore();
//        for (Store st : stores){
//            System.out.println(st.getInventory());
//            System.out.println(st.getProduct());
//        }
//    }
}
