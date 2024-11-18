package com.poly.datn.Entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.datn.Entity.IsDelete;
import com.poly.datn.Entity.Order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fullname", nullable = false, columnDefinition = "nvarchar(255)")
    private String fullname;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "address", nullable = false, columnDefinition = "nvarchar(255)")
    private String address;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "gender", nullable = false)
    private Boolean gender;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "avata", columnDefinition = "nvarchar(255)")
    private String avata;
    @Column(name = "updateat", nullable = false)
    private LocalDate updateAt;
    @Column(name = "createat", nullable = false)
    private LocalDate createAt;

    @Column(name = "is_deleted")
    private int isDeleted = IsDelete.ACTIVE.getValue();

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Account> accounts;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Order> orders;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Cart cart;

    @PrePersist
    public void onCreate() {
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }


    @PreUpdate
    public void onUpdate() {
        this.updateAt = LocalDate.now();
    }
}