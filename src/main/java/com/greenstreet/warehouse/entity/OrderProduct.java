package com.greenstreet.warehouse.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "warehouse",name = "order_products")
public class OrderProduct {

    @Id
    private UUID id;

    @Column(name = "count_")
    private Integer count;

    private Double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id", nullable=false)
    @JsonIgnore
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="order_products_status_id", nullable=false)
    private OrderProductStatus status;
}
