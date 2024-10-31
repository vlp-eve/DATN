package com.poly.datn.Entity.Product;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "store")
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "inventory_id", nullable = false, referencedColumnName = "id")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;
}
