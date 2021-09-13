package com.greenstreet.warehouse.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "warehouse",name = "orders")
public class Order extends BaseAuditingEntity{

    @Id
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="delivery_schedule_id", nullable=false)
    private DeliverySchedule deliverySchedule;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="status_id", nullable=false)
    private OrderStatus status;

    @OneToMany(mappedBy="order")
    private Set<OrderProduct> orderProducts;

}
