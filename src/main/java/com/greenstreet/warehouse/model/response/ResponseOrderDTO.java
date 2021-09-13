package com.greenstreet.warehouse.model.response;

import com.greenstreet.warehouse.entity.DeliverySchedule;
import com.greenstreet.warehouse.entity.Order;
import com.greenstreet.warehouse.entity.OrderStatus;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ResponseOrderDTO {
    private UUID id;
    private DeliverySchedule deliveryDate;
    private OrderStatus status;
    private Set<ResponseOrderProductDTO> products;

    public ResponseOrderDTO() {
    }

    public ResponseOrderDTO(Order order) {
        this.id = order.getId();
        this.deliveryDate = order.getDeliverySchedule();
        this.status = order.getStatus();
        products = order
                .getOrderProducts()
                .stream()
                .map(ResponseOrderProductDTO::new)
                .collect(Collectors.toSet());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public DeliverySchedule getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(DeliverySchedule deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<ResponseOrderProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ResponseOrderProductDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseOrderDTO that = (ResponseOrderDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(deliveryDate, that.deliveryDate) &&
                Objects.equals(status, that.status) && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deliveryDate, status, products);
    }
}
