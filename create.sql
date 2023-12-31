
    create table beer (
       id varchar(36) not null,
        create_date datetime(6),
        name varchar(50) not null,
        price decimal(38,2) not null,
        quantity_on_hand integer,
        style smallint not null,
        upc varchar(255) not null,
        update_date datetime(6),
        version integer,
        primary key (id)
    ) engine=InnoDB;

    create table customer (
       id varchar(36) not null,
        create_date datetime(6),
        name varchar(255),
        update_date datetime(6),
        version integer,
        primary key (id)
    ) engine=InnoDB;

    drop table if exists beer;

    drop table if exists customer;

    create table beer (
       id varchar(36) not null,
        create_date datetime(6),
        name varchar(50) not null,
        price decimal(38,2) not null,
        quantity_on_hand integer,
        style smallint not null,
        upc varchar(255) not null,
        update_date datetime(6),
        version integer,
        primary key (id)
    ) engine=InnoDB;

    create table customer (
       id varchar(36) not null,
        create_date datetime(6),
        name varchar(255),
        update_date datetime(6),
        version integer,
        primary key (id)
    ) engine=InnoDB;
