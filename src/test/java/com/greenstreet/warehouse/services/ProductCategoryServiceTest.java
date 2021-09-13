package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.ProductCategory;
import com.greenstreet.warehouse.repository.ProductCategoryRepository;
import com.greenstreet.warehouse.services.impl.ProductCategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductCategoryServiceTest {

    @Mock
    ProductCategoryRepository categoryRepository;

    @InjectMocks
    ProductCategoryServiceImpl categoryService;

    @Test
    void create() {
        ProductCategory expected = getTestCategory();
        given(categoryRepository.save(any())).willReturn(expected);

        assertThat(categoryService.create(new ProductCategory())).isEqualTo(expected);
    }

    @Test
    void update() {
        ProductCategory productCategory = getTestCategory();
        given(categoryRepository.findById(productCategory.getId())).willReturn(Optional.of(productCategory));
        given(categoryRepository.save(any())).willReturn(productCategory);

        assertThat(categoryService.update(productCategory)).isEqualTo(productCategory);
    }

    @Test
    void getAll() {
        List<ProductCategory> categoryList = List.of(getTestCategory());
        given(categoryRepository.findAll()).willReturn(categoryList);

        assertThat(categoryService.getAll()).isEqualTo(categoryList);
    }

    private ProductCategory getTestCategory(){
        return new ProductCategory(1,"cut");
    }
}