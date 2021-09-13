create table if not exists warehouse.users
(
    id uuid not null
    constraint users_pk
    primary key,
    firstname varchar(30),
    lastname varchar(30),
    email varchar(255),
    login varchar(30) not null,
    password varchar(255),
    created_by varchar(30),
    created_date timestamp,
    updated_by varchar(30),
    updated_date timestamp,
    status integer
    );

comment on table warehouse.users is 'Пользователи';

comment on column warehouse.users.firstname is 'Имя';

comment on column warehouse.users.lastname is 'Фамилия';

comment on column warehouse.users.login is 'Логин пользователя';

comment on column warehouse.users.password is 'Пароль';

comment on column warehouse.users.created_by is 'Создано';

comment on column warehouse.users.created_date is 'Дата создания';

comment on column warehouse.users.updated_by is 'Обновлено';

comment on column warehouse.users.updated_date is 'Дата обновления';

comment on column warehouse.users.status is 'Статус пользователя';

create unique index if not exists users_username_uindex
	on warehouse.users (login);

--Roles
create table if not exists warehouse.u_roles
(
    id serial not null
        constraint roles_pk
            primary key,
    name varchar(25) not null
);

--Link userToRoles
create table if not exists warehouse.users_roles
(
    user_id uuid not null
        constraint users_roles_users_id_fk
            references warehouse.users,
    role_id int not null
        constraint users_roles_u_roles_id_fk
            references warehouse.u_roles
);