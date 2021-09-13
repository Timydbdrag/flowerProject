package com.greenstreet.warehouse.entity;


import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@Table(schema = "warehouse", name = "product")
public class Product extends BaseAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_")
    private String name;
    private String description;
    private Integer height;
    private Double price;
    private Integer multiply;
    @Column(name = "delivery_days")
    private Integer deliveryDays;
    @Column(name = "img_link")
    private String imgLink;

    @Column(name = "is_deleted")
    @Type(type= "org.hibernate.type.NumericBooleanType")
    private Boolean isDeleted;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "color_id", nullable=false)
    private Color color;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="country_id", nullable=false)
    private Country country;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="category_id", nullable=false)
    private ProductCategory category;
}
