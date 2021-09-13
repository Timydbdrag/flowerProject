create table if not exists warehouse.orders
(
    id uuid
        constraint orders_pk
            primary key,
    user_id uuid
        constraint orders_users_id_fk
            references warehouse.users,
    delivery_schedule_id int
        constraint orders_delivery_schedule_id_fk
            references warehouse.delivery_schedule,
    status_id int
        constraint orders_status_id_fk
            references warehouse.order_status,
    created_date timestamp,
    created_by varchar(30),
    updated_date timestamp,
    updated_by varchar(30)
);
comment on table warehouse.orders is 'заказы';