package com.greenstreet.warehouse.model.request;

import java.util.Set;
import java.util.UUID;

public class OrderDTOAdmin {
    private UUID id;
    private Integer deliveryId;
    private Integer statusId;
    private Set<OrderProductDTO> products;

    public OrderDTOAdmin() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Set<OrderProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderProductDTO> products) {
        this.products = products;
    }
}
