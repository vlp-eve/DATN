package com.datn.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User1 {
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
	
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Account> accounts;
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Order> orders;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Cart cart;
	
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