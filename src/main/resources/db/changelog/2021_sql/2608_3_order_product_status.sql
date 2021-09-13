create table if not exists warehouse.order_product_status
(
    id serial
        constraint order_product_status_pk
            primary key,
    name_ varchar(30)
);

comment on table warehouse.order_product_status is 'статус продукта в заказе';

INSERT INTO warehouse.order_product_status (id, name_) VALUES (1, 'Ожидает подтверждения');