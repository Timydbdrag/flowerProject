package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    ProductCategory create(ProductCategory category);
    ProductCategory update(ProductCategory category);
    List<ProductCategory> getAll();
}
