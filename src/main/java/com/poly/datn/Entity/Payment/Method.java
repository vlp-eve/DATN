package com.poly.datn.Entity.Payment;


import com.poly.datn.Entity.MethodName;
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
@Table(name = "method")
public class Method {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private MethodName methodName;

    @Column(name = "description")
    private String description;

    @Column(name = "img")
    private String img;
}
