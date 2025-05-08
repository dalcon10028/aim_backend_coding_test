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

create table if not exists account
(
    id         bigint auto_increment primary key,
    user_id    bigint not null,
    created_at timestamp default current_timestamp
) engine = InnoDB
  default charset = utf8mb4;

create table if not exists transaction
(
    id         bigint auto_increment primary key,
    account_id bigint       not null,
    type       varchar(255) not null comment 'DEPOSIT, WITHDRAW',
    amount     bigint       not null,
    created_at timestamp default current_timestamp
) engine = InnoDB
  default charset = utf8mb4;

create table if not exists security
(
    id         bigint auto_increment primary key,
    name       varchar(255) not null,
    ticker     varchar(255) not null,
    price      bigint       not null,
    deleted_at timestamp default null,
    updated_at timestamp default current_timestamp on update current_timestamp,
    created_at timestamp default current_timestamp,
    unique key (ticker)
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

create trigger if not exists security_insert_trigger
    before insert
    on security
    for each row
begin
    insert into security_log (security_id, name, ticker, price, created_at)
    values (new.id, new.name, new.ticker, new.price, new.created_at);
end;

create trigger if not exists security_update_trigger
    before update
    on security
    for each row
begin
    insert into security_log (security_id, name, ticker, price, created_at)
    values (old.id, old.name, old.ticker, old.price, old.updated_at);
end;

insert into security (id, name, ticker, price)
values (1, '삼성전자', '005930', 54600),
       (2, 'SK하이닉스', '000660', 190300),
       (3, 'LG에너지솔루션', '373220', 327500),
       (4, '삼성바이오로직스', '207940', 1036000),
       (5, '한화에어로스페이스', '012450', 883000),
       (6, '현대차', '005380', 187100),
       (7, 'HD현대중공업', '329180', 421500),
       (8, 'KB금융', '105560', 94500),
       (9, '삼성전자우', '005935', 45100),
       (10, '셀트리온', '068270', 159400);