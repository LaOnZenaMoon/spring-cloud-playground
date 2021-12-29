drop table if exists catalogs
drop table if exists orders
drop table if exists users
create table catalogs (id bigint not null auto_increment, created_date_time datetime(6) default CURRENT_TIMESTAMP not null, product_id varchar(255) not null, product_name varchar(50) not null, stock integer not null, unit_price integer not null, primary key (id)) engine=InnoDB
create table orders (id bigint not null auto_increment, created_date_time datetime(6) default CURRENT_TIMESTAMP not null, order_id varchar(255) not null, quantity integer not null, total_price integer not null, unit_price integer not null, user_id varchar(255) not null, primary key (id)) engine=InnoDB
create table users (id bigint not null auto_increment, email varchar(50) not null, encrypted_password varchar(255) not null, name varchar(50) not null, user_id varchar(255) not null, primary key (id)) engine=InnoDB
alter table catalogs add constraint UK_o9fqyb4x6sl5e9o60mxjaem62 unique (product_id)
alter table orders add constraint UK_hmsk25beh6atojvle1xuymjj0 unique (order_id)
alter table users add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email)
alter table users add constraint UK_8ajv6sq8i5fdnjtqctuw49g86 unique (encrypted_password)
alter table users add constraint UK_6efs5vmce86ymf5q7lmvn2uuf unique (user_id)