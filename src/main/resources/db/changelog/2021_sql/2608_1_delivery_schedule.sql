create table if not exists warehouse.delivery_schedule
(
    id serial
        constraint delivery_schedule_pk
            primary key,
    date_ date
);

comment on table warehouse.delivery_schedule is 'расписание поставок';
