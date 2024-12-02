package com.poly.datn.Entity.Product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.poly.datn.Entity.IsDelete;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Giá bán không được để trống")
    @DecimalMin(value = "0.1", message = "Giá bán phải lớn hơn 0")
    @Column(name = "price", nullable = false)
    private Double price;

    @NotBlank(message = "Mô tả không được để trống")
    @Column(name = "description", nullable = false)
    private String description;



    @Column(name = "img_banner", nullable = false)
    private String imgBannerPath;

    @Column(name = "total_quantity", nullable = false)
    private Double totalQuantity;

    @Column(name = "available", nullable = false)
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false, referencedColumnName = "id")
    private Unit unit;

    @NotBlank(message = "Tên sản phẩm không được để trống")
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
