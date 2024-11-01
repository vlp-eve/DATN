package com.poly.datn.Entity.Cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.datn.Entity.Product.Store;
import com.poly.datn.Entity.Product.Unit;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "cart_detail")
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="quantity", nullable = true)
    private int quantity;

    @Column(name="price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false, referencedColumnName = "id")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false, referencedColumnName = "id")
    @JsonIgnore
    private Store store;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @JsonBackReference
    private Cart cart;
}
