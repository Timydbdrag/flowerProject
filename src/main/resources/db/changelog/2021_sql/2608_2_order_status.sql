create table if not exists warehouse.order_status
(
    id serial
        constraint order_status_pk
            primary key,
    name_ varchar(20)
);

comment on table warehouse.order_status is 'Статус заказа';

INSERT INTO warehouse.order_status (id, name_) VALUES (1, 'В обработке');