package com.poly.datn.Entity.Cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.poly.datn.Entity.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "create_date")
    private LocalDate createDate;
}
