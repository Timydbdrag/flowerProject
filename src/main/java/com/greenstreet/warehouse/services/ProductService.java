package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.Product;
import com.greenstreet.warehouse.model.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> getProducts(Pageable pageable);
    Product getProductById(Long id);
    Product create(ProductDTO productDTO);
    Product update(ProductDTO product);
}
