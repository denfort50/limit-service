create table if not exists limits
(
    id bigserial primary key,
    sum double precision not null
);