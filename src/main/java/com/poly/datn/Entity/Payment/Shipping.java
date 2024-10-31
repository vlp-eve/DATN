package com.poly.datn.Entity.Payment;

import com.poly.datn.Entity.Order.Order;
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
@Table(name = "shipping")
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "address")
    private String address;

    @Column( name = "phone")
    private String phone;

    @Column( name="note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "method_id", nullable = false, referencedColumnName = "id")
    private Method method;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, referencedColumnName = "id")
    private Order order;
}
