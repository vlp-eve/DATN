package com.datn.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "total_amount", nullable = false)
	private Double totalAmount;
	
	@Column(name = "createDate", nullable = false)
	private LocalDate createDate;
	
	@Column(name = "status", nullable = false, columnDefinition = "nvarchar(50)")
	private String Status;
	
	@Column(name = "address", nullable = false, columnDefinition = "nvarchar(255)")
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User1 user;
	
	@OneToOne(mappedBy = "order")
	private Shipping shipping;
	
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetails;
	
}
