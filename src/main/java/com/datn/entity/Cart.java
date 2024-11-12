package com.datn.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "total_price", nullable = false)
	private Double totalPrice;
	
	@Column(name = "create_date", nullable = false)
	private LocalDate createDate;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
	private User1 user;
	
	@OneToMany(mappedBy = "cart")
	private List<CartDetail> cartDetails;
}
