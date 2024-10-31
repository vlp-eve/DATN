package com.poly.datn.Entity.Product;

import com.poly.datn.Entity.IsDelete;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "unit")
@AllArgsConstructor
@NoArgsConstructor
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Mã đơn vị

    @Column(name = "name", nullable = false, length = 255)
    private String name; // Tên đơn vị

    @Column(name = "is_deleted")
    private int isDeleted = IsDelete.ACTIVE.ordinal();


}
