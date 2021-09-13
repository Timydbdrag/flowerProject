package com.greenstreet.warehouse.model;

import com.greenstreet.warehouse.entity.Color;
import com.greenstreet.warehouse.entity.Country;
import com.greenstreet.warehouse.entity.Product;
import com.greenstreet.warehouse.entity.ProductCategory;

public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Integer height;
    private Double price;
    private Integer multiply;
    private Integer deliveryDays;
    private String imgLink;
    private Boolean isDeleted;
    private Color color;
    private Country country;
    private ProductCategory category;

    public ProductDTO() { }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.height = product.getHeight();
        this.price = product.getPrice();
        this.multiply = product.getMultiply();
        this.deliveryDays = product.getDeliveryDays();
        this.imgLink = product.getImgLink();
        this.isDeleted = product.getIsDeleted();
        this.color = product.getColor();
        this.country = product.getCountry();
        this.category = product.getCategory();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMultiply() {
        return multiply;
    }

    public void setMultiply(Integer multiply) {
        this.multiply = multiply;
    }

    public Integer getDeliveryDays() {
        return deliveryDays;
    }

    public void setDeliveryDays(Integer deliveryDays) {
        this.deliveryDays = deliveryDays;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }
}
