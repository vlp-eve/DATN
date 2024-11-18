package com.poly.datn.Entity.Order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.poly.datn.Entity.Payment.Method;
import com.poly.datn.Entity.Payment.Shipping;
import com.poly.datn.Entity.StatusOrder;
import com.poly.datn.Entity.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "create_date")
    private LocalDate createDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusOrder status = StatusOrder.PENDING;


    @ManyToOne
    @JoinColumn(name = "method_id", referencedColumnName = "id")
    @JsonBackReference
    private Method method;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private Shipping shipping;


}
