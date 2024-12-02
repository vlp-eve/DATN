package com.poly.datn.Entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.datn.Entity.IsDelete;
import com.poly.datn.Entity.Order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fullname", nullable = false, columnDefinition = "nvarchar(255)")
    @NotBlank(message = "Không được để trống họ và tên")
    private String fullname;
    @Email(message = "Email sai định dạng")
    @NotBlank(message = "Không được để trống email")
    @Column(name = "email", nullable = false)
    private String email;
    @NotBlank(message = "Không được để trống địa chỉ")
    @Column(name = "address", nullable = false, columnDefinition = "nvarchar(255)")
    private String address;
    @Column(name = "phone", nullable = false)
    @NotBlank(message = "Chưa nhập số điện thoại cá nhân")
    @Pattern(regexp = "^\\d{10,11}$", message = "Số điện thoại phải có 10 hoặc 11 số")
    private String phone;
    @NotNull(message = "Bạn chưa chọn giới tính")
    @Column(name = "gender", nullable = false)
    private Boolean gender;
    @Column(name = "username", nullable = false)
    @NotBlank(message = "Bạn chưa nhập username")
    private String username;
    @Column(name = "password", nullable = false)
    @NotBlank(message = "Bạn chưa nhập password")
    @Size(min = 6,message = "Password phải có trên 6 ký tự")
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