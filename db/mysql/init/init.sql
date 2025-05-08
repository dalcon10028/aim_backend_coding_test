create table if not exists users
(
    id       int auto_increment primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    unique key (username)
) engine = InnoDB
  default charset = utf8mb4;

create table if not exists login_history
(
    id         int auto_increment primary key,
    username   varchar(255) not null,
    action     varchar(255) not null comment 'LOGIN, LOGOUT',
    created_at timestamp default current_timestamp
) engine = InnoDB
  default charset = utf8mb4;