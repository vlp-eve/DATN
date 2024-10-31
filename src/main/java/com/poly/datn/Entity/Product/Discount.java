package com.poly.datn.Entity.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "discount")
@AllArgsConstructor
@NoArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "discountPercentage", nullable = false)
    private Integer discountPercentage;

    @OneToMany(mappedBy = "discount")
    @JsonBackReference
    private List<Store> stores;

    @Column(name = "name", nullable = false)
    private String name;
//    @Column(name = "status", nullable = false)
//    private String status;
}
