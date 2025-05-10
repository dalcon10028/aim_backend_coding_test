create database if not exists aim;
use aim;

# 1.User
create table if not exists users
(
    id       bigint auto_increment primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    unique key (username)
) engine = InnoDB
  default charset = utf8mb4;

create table if not exists login_history
(
    id         bigint auto_increment primary key,
    username   varchar(255) not null,
    action     varchar(255) not null comment 'LOGIN, LOGOUT',
    created_at timestamp default current_timestamp
) engine = InnoDB
  default charset = utf8mb4;

# 2.Account
create table if not exists account
(
    id         bigint auto_increment primary key,
    user_id    bigint       not null,
    risk_type  varchar(255) not null comment 'FULL_BALANCE, HALF_BALANCE',
    created_at timestamp default current_timestamp
) engine = InnoDB
  default charset = utf8mb4;

create table if not exists transaction
(
    id         bigint auto_increment primary key,
    account_id bigint       not null,
    type       varchar(255) not null comment 'DEPOSIT, WITHDRAW',
    amount     bigint       not null,
    delta      bigint       not null,
    created_at timestamp default current_timestamp
) engine = InnoDB
  default charset = utf8mb4;


# 3.Security

create table if not exists security
(
    id         bigint auto_increment primary key,
    name       varchar(255) not null,
    ticker     varchar(255) not null,
    price      bigint       not null,
    deleted_at timestamp default null,
    updated_at timestamp default current_timestamp on update current_timestamp,
    created_at timestamp default current_timestamp,
    index idx_ticker (ticker)
) engine = InnoDB
  default charset = utf8mb4;

create table if not exists security_log
(
    id          bigint auto_increment primary key,
    security_id bigint       not null,
    name        varchar(255) not null,
    ticker      varchar(255) not null,
    price       bigint       not null,
    created_at  timestamp default current_timestamp
) engine = InnoDB
  default charset = utf8mb4;

delimiter $$
create trigger if not exists security_insert_trigger
    after insert
    on security
    for each row
begin
    insert into security_log (security_id, name, ticker, price, created_at)
    values (new.id, new.name, new.ticker, new.price, new.created_at);
end$$
delimiter ;

delimiter $$
create trigger if not exists security_update_trigger
    after update
    on security
    for each row
begin
    if old.price != new.price then
        insert into security_log (security_id, name, ticker, price, created_at)
        values (new.id, new.name, new.ticker, new.price, new.updated_at);
    end if;
end$$
delimiter ;

insert into security (name, ticker, price)
values ('삼성전자', '005930', 54600),
       ('SK하이닉스', '000660', 190300),
       ('LG에너지솔루션', '373220', 327500),
       ('삼성바이오로직스', '207940', 1036000),
       ('한화에어로스페이스', '012450', 883000),
       ('현대차', '005380', 187100),
       ('HD현대중공업', '329180', 421500),
       ('KB금융', '105560', 94500),
       ('삼성전자우', '005935', 45100),
       ('셀트리온', '068270', 159400);

# 4.Portfolio

create table if not exists model_portfolio
(
    id         bigint auto_increment primary key,
    risk_type  varchar(255)   not null comment 'FULL_BALANCE, HALF_BALANCE',
    ticker     varchar(255)   not null,
    weight     decimal(10, 4) not null,
    created_at timestamp default current_timestamp
) engine = InnoDB
  default charset = utf8mb4;

create table if not exists actual_portfolio
(
    id          bigint auto_increment primary key,
    risk_type   varchar(255)   not null comment 'FULL_BALANCE, HALF_BALANCE',
    account_id  bigint         not null,
    advisory_id bigint         not null,
    ticker      varchar(255)   not null,
    weight      decimal(10, 4) not null,
    quantity    bigint         not null,
    price       bigint         not null,
    created_at  timestamp default current_timestamp
) engine = InnoDB
  default charset = utf8mb4;