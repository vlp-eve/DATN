package com.poly.datn.Entity.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.poly.datn.Entity.IsDelete;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @JsonBackReference
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "is_deleted")
    private int isDeleted = IsDelete.ACTIVE.ordinal();

    @Column(name = "receivedDate", nullable = false)
    private LocalDate receivedDate;  // Ngày nhập kho

    @Column(name = "expiryDate", nullable = false)
    private LocalDate expiryDate;  // Ngày hết hạn

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;




}
