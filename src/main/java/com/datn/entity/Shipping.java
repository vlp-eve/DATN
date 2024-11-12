package com.datn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "shipping")
public class Shipping {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "address", nullable = false, columnDefinition = "nvarchar(255)")
	private String address;
	
	@Column(name="phone", nullable = false)
	private String phone;
	
	@Column(name = "note", nullable = false, columnDefinition = "nvarchar(255)")
	private String note;
	
	@OneToOne
	@JoinColumn(name = "methodpay_id", nullable = false, referencedColumnName = "id")
	private MethodPay methodpay;
	
	@OneToOne
	@JoinColumn(name = "order_id", nullable = false, referencedColumnName = "id")
	private Order order;
}
