package com.datn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "methodpay")
public class MethodPay {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, columnDefinition = "nvarchar(50)")
	private String nameMethod;
	
	@Column(name = "description", columnDefinition = "nvarchar(255)")
	private String description;
	
	@Column(name = "img", columnDefinition = "nvarchar(255)")
	private String img;
	
	@OneToOne(mappedBy = "methodpay")
	private Shipping shipping;
}
