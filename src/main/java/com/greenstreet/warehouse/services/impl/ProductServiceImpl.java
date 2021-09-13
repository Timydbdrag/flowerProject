package com.greenstreet.warehouse.services.impl;

import com.greenstreet.warehouse.entity.Product;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.model.ProductDTO;
import com.greenstreet.warehouse.repository.ColorRepository;
import com.greenstreet.warehouse.repository.CountryRepository;
import com.greenstreet.warehouse.repository.ProductCategoryRepository;
import com.greenstreet.warehouse.repository.ProductRepository;
import com.greenstreet.warehouse.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.greenstreet.warehouse.exception.ExceptionConstant.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final CountryRepository countryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public Page<Product> getProducts(Pageable pageable) {
        if (!onlyContainsAllowedProperties(pageable))
            throw new ApiRequestException(SORTED_PARAM_NOT_FOUND);

        log.debug("Getting all product");
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProductById(Long id) {
        log.debug("Getting product of ID: " + id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND));
    }

    @Override
    public Product create(ProductDTO productDTO) {
        if(colorRepository.findById(productDTO.getColor().getId()).isEmpty())
            throw new ApiRequestException(COLOR_NOT_FOUND);

        if(countryRepository.findById(productDTO.getCountry().getId()).isEmpty())
            throw new ApiRequestException(COUNTRY_NOT_FOUND);

        if(productCategoryRepository.findById(productDTO.getCategory().getId()).isEmpty())
            throw new ApiRequestException(CATEGORY_NOT_FOUND);

        Product product = new Product();
        productDtoToProduct(productDTO,product);

        log.info("Save new Product to the DB, name: {} ", productDTO.getName());
        return productRepository.save(product);
    }

    @Override
    public Product update(ProductDTO productDTO) {
        if (productDTO.getId() == null) throw new ApiRequestException("Product ID cannot be null!");

        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ApiRequestException(PRODUCT_NOT_FOUND));

        if(colorRepository.findById(productDTO.getColor().getId()).isEmpty())
            throw new ApiRequestException(COLOR_NOT_FOUND);

        if(countryRepository.findById(productDTO.getCountry().getId()).isEmpty())
            throw new ApiRequestException(COUNTRY_NOT_FOUND);

        if(productCategoryRepository.findById(productDTO.getCategory().getId()).isEmpty())
            throw new ApiRequestException(CATEGORY_NOT_FOUND);

        productDtoToProduct(productDTO, product);

        log.info("Update product in the DB, ID: {} ", product.getId());
        return productRepository.save(product);
    }

    private void productDtoToProduct(ProductDTO productDTO, Product product) {
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setHeight(productDTO.getHeight());
        product.setPrice(productDTO.getPrice());
        product.setMultiply(productDTO.getMultiply());
        product.setDeliveryDays(productDTO.getDeliveryDays());
        product.setImgLink(productDTO.getImgLink());
        product.setColor(productDTO.getColor());
        product.setCountry(productDTO.getCountry());
        product.setCategory(productDTO.getCategory());
        product.setIsDeleted(false);
    }

    private List<String> getAllowedOrderedProperties() {
        return List.of(
                "id", "name", "price", "color", "country", "deliveryDays", "height"
        );
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort()
                .stream()
                .map(Sort.Order::getProperty)
                .allMatch(getAllowedOrderedProperties()::contains);
    }
}
