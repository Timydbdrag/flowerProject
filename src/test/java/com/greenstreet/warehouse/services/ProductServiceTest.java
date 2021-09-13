package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.Color;
import com.greenstreet.warehouse.entity.Country;
import com.greenstreet.warehouse.entity.Product;
import com.greenstreet.warehouse.entity.ProductCategory;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.model.ProductDTO;
import com.greenstreet.warehouse.repository.ProductCategoryRepository;
import com.greenstreet.warehouse.repository.ColorRepository;
import com.greenstreet.warehouse.repository.CountryRepository;
import com.greenstreet.warehouse.repository.ProductRepository;
import com.greenstreet.warehouse.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ColorRepository colorRepository;

    @Mock
    CountryRepository countryRepository;

    @Mock
    ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    ProductServiceImpl productService;


    @Test
    void shouldReturnPageProducts() {
        Page<Product> page = new PageImpl<>(List.of(getTestProduct()));

        Pageable pageable = PageRequest.of(0,10, Sort.by("id"));
        given(productRepository.findAll(pageable)).willReturn(page);

        assertThat(productService.getProducts(pageable)).isEqualTo(page);
    }

    @Test
    void shouldReturnProductById() {
        Product testProduct = getTestProduct();

        given(productRepository.findById(testProduct.getId())).willReturn(Optional.of(testProduct));

        assertThat(productService.getProductById(testProduct.getId())).isEqualTo(testProduct);
    }

    @Test
    void shouldCreatedProduct() {
        Product testProduct = getTestProduct();
        given(productRepository.save(any())).willReturn(testProduct);
        given(colorRepository.findById(any())).willReturn(Optional.of(getTestColor()));
        given(countryRepository.findById(any())).willReturn(Optional.of(getTestCountry()));
        given(productCategoryRepository.findById(any())).willReturn(Optional.of(getTestCategory()));

        assertThat(productService.create(new ProductDTO(testProduct))).isEqualTo(testProduct);
    }

    @Test
    void shouldUpdatedProduct() {
        Product testProduct = getTestProduct();
        given(productRepository.save(any())).willReturn(testProduct);
        given(productRepository.findById(testProduct.getId())).willReturn(Optional.of(testProduct));
        given(colorRepository.findById(any())).willReturn(Optional.of(getTestColor()));
        given(countryRepository.findById(any())).willReturn(Optional.of(getTestCountry()));
        given(productCategoryRepository.findById(any())).willReturn(Optional.of(getTestCategory()));

        assertThat(productService.update(new ProductDTO(testProduct))).isEqualTo(testProduct);
    }

    @Test
    void shouldReturnApiRequestExceptionIdCannotBeNull() {
        ProductDTO productDTO = new ProductDTO();
        Exception exception = assertThrows(ApiRequestException.class, () -> {
            productService.update(productDTO);
        });

        String expectedMessage = "Product ID cannot be null!";

        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    private Product getTestProduct() {
        return Product.builder()
                .id(1L)
                .name("Rose")
                .description("description")
                .height(70)
                .price(100.00)
                .multiply(20)
                .deliveryDays(7)
                .imgLink("here will be link")
                .isDeleted(false)
                .color(getTestColor())
                .country(getTestCountry())
                .category(getTestCategory())
                .build();
    }

    private ProductCategory getTestCategory() {
        return new ProductCategory(2, "cut");
    }

    private Country getTestCountry() {
        return new Country(2, "Kenya");
    }

    private Color getTestColor(){
        return new Color(2, "Red");
    }
}