package com.greenstreet.warehouse.model.response;

import com.greenstreet.warehouse.entity.OrderProduct;
import com.greenstreet.warehouse.entity.OrderProductStatus;

import java.util.Objects;
import java.util.UUID;

public class ResponseOrderProductDTO {
    private UUID id;
    private Long productId;
    private Integer count;
    private Double price;
    private String image;
    private OrderProductStatus status;

    public ResponseOrderProductDTO(){}

    public ResponseOrderProductDTO(OrderProduct orderProduct){
        this.id = orderProduct.getId();
        this.productId = orderProduct.getProduct().getId();
        this.count = orderProduct.getCount();
        this.price = orderProduct.getPrice();
        this.image = orderProduct.getProduct().getImgLink();
        this.status = orderProduct.getStatus();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public OrderProductStatus getStatus() {
        return status;
    }

    public void setStatus(OrderProductStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseOrderProductDTO that = (ResponseOrderProductDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(productId, that.productId) && Objects.equals(count, that.count) && Objects.equals(price, that.price) && Objects.equals(image, that.image) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, count, price, image, status);
    }
}
