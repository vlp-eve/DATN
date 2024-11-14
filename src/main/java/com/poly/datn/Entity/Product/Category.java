package com.poly.datn.Entity.Product;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.poly.datn.Entity.IsDelete;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;


    @Column(name = "is_deleted")
    private int isDeleted = IsDelete.ACTIVE.ordinal();

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Product> products;
}
