package com.datn.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "price", nullable = false)
	private Double price;
	
	@Column(name = "description", nullable = false, columnDefinition = "nvarchar(255)")
	private String description;
	
	@Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
	private String nameProduct;
	
	@Column(name = "imgbanner", nullable = false, columnDefinition = "nvarchar(255)")
	private String imgBanner;
	
	@Column(name = "isdelete", nullable = false)
	private Integer isDelete;
	
	@Column(name = "available", nullable = false)
	private Boolean anvailable;
	
	@Column(name = "updateat", nullable = false)
	private LocalDate updateAt;
	
	@Column(name = "createat", nullable = false)
	private LocalDate createAt;
	
	@Column(name = "totalquantity", nullable = false)
	private Integer totalQuantity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "unit_id", nullable = false, referencedColumnName = "id")
	private Unit unit;
	
	@OneToMany(mappedBy = "product")
	private List<Store> store;
	
	@OneToMany(mappedBy = "product")
	private List<ProductImg> productImg;
}
