package com.datn.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "units")
public class Unit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false, columnDefinition = "nvarchar(50)")
	private String unitName;
	
	@OneToMany(mappedBy = "unit")
	private List<OrderDetail> orderDetails;
	
	@OneToMany(mappedBy = "unit")
	private List<CartDetail> cartDetails;
	
	@OneToMany(mappedBy = "unit")
	private List<Product> products;
}
