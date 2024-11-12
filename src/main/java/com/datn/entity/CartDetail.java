package com.datn.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "cart_detail")
public class CartDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "price", nullable = false)
	private Double price;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "cart_id", nullable = false, referencedColumnName = "id")
	@JsonBackReference
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name = "store_id", nullable = false, referencedColumnName = "id")
	private Store store;
	
	@ManyToOne
	@JoinColumn(name = "unit_id", nullable = false, referencedColumnName = "id")
	private Unit unit;
}
