create table brandes
(
    id   bigint       not null auto_increment,
    name varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table carts
(
    id          bigint         not null auto_increment,
    count       INT UNSIGNED   not null,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    price       decimal(19, 2) not null,
    update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    item_id     bigint,
    order_id    bigint,
    primary key (id)
) engine = InnoDB;
create table categories
(
    id    bigint       not null auto_increment,
    image varchar(255) not null,
    name  varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table colors
(
    id   bigint       not null auto_increment,
    name varchar(255) not null,
    primary key (id)
) engine = InnoDB;
create table item_details
(
    item_id     bigint not null,
    brand_id    bigint,
    category_id bigint not null,
    color_id    bigint,
    primary key (item_id, category_id)
) engine = InnoDB;
create table items
(
    id          bigint         not null auto_increment,
    count       INT UNSIGNED,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    image       varchar(255)   not null,
    name        varchar(255)   not null,
    price       decimal(19, 2) not null,
    update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    primary key (id)
) engine = InnoDB;
create table orders
(
    id      bigint not null auto_increment,
    status  varchar(255),
    user_id bigint,
    primary key (id)
) engine = InnoDB;
create table user_role
(
    user_id bigint not null,
    roles   varchar(255)
) engine = InnoDB;
create table users
(
    id         bigint       not null auto_increment,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    login      varchar(255) not null,
    password   varchar(255) not null,
    status     varchar(255),
    primary key (id)
) engine = InnoDB;
alter table item_details
    add constraint item_details_unique unique (item_id);
alter table carts
    add constraint cart_item_fk foreign key (item_id) references items (id);
alter table carts
    add constraint cart_order_fk foreign key (order_id) references orders (id);
alter table item_details
    add constraint item_details_brand_fk foreign key (brand_id) references brandes (id);
alter table item_details
    add constraint item_details_category_fk foreign key (category_id) references categories (id);
alter table item_details
    add constraint item_details_color_fk foreign key (color_id) references colors (id);
alter table item_details
    add constraint item_details_item_fk foreign key (item_id) references items (id);
alter table orders
    add constraint order_user_fk foreign key (user_id) references users (id);
alter table user_role
    add constraint user_role_user_fk foreign key (user_id) references users (id);
