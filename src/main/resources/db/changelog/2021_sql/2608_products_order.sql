create table if not exists warehouse.order_products
(
    id uuid
        constraint order_products_pk
            primary key,
    order_id uuid
        constraint order_products_orders_id_fk
            references warehouse.orders,
    product_id bigint
        constraint order_products_product_id_fk
            references warehouse.product,
    count_ int,
    price numeric(10,2),
    order_products_status_id int
        constraint order_products_order_product_status_id_fk
            references warehouse.order_product_status
);

comment on table warehouse.order_products is 'продукты в заказе';

comment on column warehouse.order_products.order_id is 'ид заказа';

comment on column warehouse.order_products.product_id is 'ид продукта';

comment on column warehouse.order_products.count_ is 'количество продукта';

comment on column warehouse.order_products.price is 'стоимость покупки';