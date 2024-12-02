package com.poly.datn.Entity.DTO;

import com.poly.datn.Entity.Product.Discount;
import com.poly.datn.Entity.Product.Inventory;
import com.poly.datn.Entity.Product.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDTO {



    private Long storeId;
    private Discount discount;
    private Product product;

    private Inventory inventory;

    public StoreDTO(Long storeId, Discount discount, Product product, Inventory inventory) {
      this.storeId = storeId;
      this.discount = discount;
      this.product = product;
      this.inventory = inventory;
    }



}
