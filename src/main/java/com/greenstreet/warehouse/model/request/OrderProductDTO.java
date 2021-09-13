package com.greenstreet.warehouse.model.request;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OrderProductDTO {
    private UUID id;
    private UUID orderID;
    @NotNull
    private Long productId;
    @NotNull
    private Integer count;
    private Double price;
    private Integer status;

    public OrderProductDTO(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderID() {
        return orderID;
    }

    public void setOrderID(UUID orderID) {
        this.orderID = orderID;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
