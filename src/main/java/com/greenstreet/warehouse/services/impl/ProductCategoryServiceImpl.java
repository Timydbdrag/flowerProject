package com.greenstreet.warehouse.services.impl;

import com.greenstreet.warehouse.entity.ProductCategory;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.repository.ProductCategoryRepository;
import com.greenstreet.warehouse.services.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.greenstreet.warehouse.exception.ExceptionConstant.PRODUCT_CATEGORY_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory create(ProductCategory category) {
        return productCategoryRepository.save(category);
    }

    @Override
    public ProductCategory update(ProductCategory category) {
        if (category.getId() == null) throw new ApiRequestException("Category ID cannot be null!");

        return Optional.of(productCategoryRepository.findById(category.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(productCategory -> {
                    log.info("Update category in the DB, ID: {} ", category.getId());
                    productCategory.setName(category.getName());
                    return productCategoryRepository.save(productCategory);

                })
                .orElseThrow(() -> new ApiRequestException(PRODUCT_CATEGORY_NOT_FOUND));
    }

    @Override
    public List<ProductCategory> getAll() {
        return productCategoryRepository.findAll();
    }
}
