package com.greenstreet.warehouse.model.request;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class OrderDTO {
    @NotNull
    private Integer deliveryScheduleId;
    @NotNull
    private Set<OrderProductDTO> products;

    public OrderDTO(){}

    public Integer getDeliveryScheduleId() {
        return deliveryScheduleId;
    }

    public void setDeliveryScheduleId(Integer deliveryScheduleId) {
        this.deliveryScheduleId = deliveryScheduleId;
    }

    public Set<OrderProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<OrderProductDTO> products) {
        this.products = products;
    }
}
