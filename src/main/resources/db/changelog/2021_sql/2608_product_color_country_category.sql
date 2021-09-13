create table if not exists warehouse.colors
(
    id serial not null
    constraint colors_pk
    primary key,
    name_ varchar(50) not null
    );

create table if not exists warehouse.country
(
    id serial not null
    constraint country_pk
    primary key,
    name_ varchar(30) not null
    );

create table if not exists warehouse.product_category
(
    id serial
        constraint product_category_pk
            primary key,
    name_ varchar(50)
);

create table if not exists warehouse.product
(
    id bigserial
        constraint product_pk
            primary key,
    name_ varchar(255),
    description text,
    height int,
    color_id int
        constraint product_colors_id_fk
            references warehouse.colors,
    country_id int
        constraint product_country_id_fk
            references warehouse.country,
    price numeric(10,2),
    multiply int,
    delivery_days int,
    img_link text,
    category_id int
        constraint product_product_category_id_fk
            references warehouse.product_category,
    created_date timestamp,
    created_by varchar(30),
    updated_date timestamp,
    updated_by varchar(30),
    is_deleted smallint not null
);

comment on column warehouse.product.name_ is 'название продукта';

comment on column warehouse.product.description is 'описание продукта';

comment on column warehouse.product.height is 'высота ';

comment on column warehouse.product.color_id is 'ИД цвета';

comment on column warehouse.product.country_id is 'ИД страны производителя';

comment on column warehouse.product.price is 'стоимость за 1 еденицу продукта';

comment on column warehouse.product.multiply is 'количество в упаковке / минимальное количество к покупке';

comment on column warehouse.product.delivery_days is 'количество дней для доставки';

comment on column warehouse.product.is_deleted is '0 - в продаже. 1 - удалено ';
