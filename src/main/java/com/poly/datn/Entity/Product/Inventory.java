package com.poly.datn.Entity.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.poly.datn.Entity.IsDelete;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Double quantity;

    @Column(name = "is_deleted")
    private int isDeleted = IsDelete.ACTIVE.getValue();

    @Column(name = "receivedDate", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receivedDate;  // Ngày nhập kho

    @Column(name = "expiryDate", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @PrePersist
    @PreUpdate
    public void roundQuantity() {
        if (quantity != null) {
            quantity = Math.round(quantity * 10.0) / 10.0; // Làm tròn 1 chữ số thập phân
        }
    }


}
