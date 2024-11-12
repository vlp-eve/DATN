package com.datn.entity;

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
@Table(name = "productimg")
public class ProductImg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "image", nullable = false, columnDefinition = "nvarchar(255)")
	private String image;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
	private Product product;
}
