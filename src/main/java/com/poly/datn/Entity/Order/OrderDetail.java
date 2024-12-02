package com.poly.datn.Entity.Order;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.poly.datn.Entity.Product.Store;
import com.poly.datn.Entity.Product.Unit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="quantity", nullable = true)
    private Double quantity;

    @Column(name="price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false, referencedColumnName = "id")
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false, referencedColumnName = "id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "orders_id", referencedColumnName = "id")
    @JsonBackReference
    private Order order;
}
