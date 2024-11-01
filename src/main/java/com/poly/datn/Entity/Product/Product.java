package com.poly.datn.Entity.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.poly.datn.Entity.IsDelete;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "description", nullable = false)
    private String description;



    @Column(name = "img_banner", nullable = false)
    private String imgBannerPath;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "available", nullable = false)
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false, referencedColumnName = "id")
    private Unit unit;


    @Column(name = "name", nullable = false, length = 225)
    private String name;
    @Column(name = "createdAt", nullable = false)
    private LocalDate createdAt;

    @Column(name = "updateAt", nullable = false)
    private LocalDate updateAt;

    @Column(name = "is_deleted")
    private int isDeleted = IsDelete.ACTIVE.ordinal();

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<Inventory> inventories;


}
