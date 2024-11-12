package com.datn.entity;

import java.util.List;

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
@Table(name = "stores")
public class Store {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "discountcode_id", nullable = false, referencedColumnName = "id")
	private DiscountCode discountcode;
	
	@OneToOne
	@JoinColumn(name = "inventory_id", nullable = false, referencedColumnName = "id")
	private Inventory inventory;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
	private Product product;
	
	@OneToMany(mappedBy = "store")
	private List<OrderDetail> orderDetails;
	
	@OneToMany(mappedBy = "store")
	private List<CartDetail> cartDetails;
	
	
}
