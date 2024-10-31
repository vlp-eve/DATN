package com.poly.datn.Entity.DTO;

import com.poly.datn.Entity.Product.Category;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Category} entity
 */
@Data
public class CategoryDto implements Serializable {
    private final Long id;
    private final String name;
    private final boolean isDelete;
}