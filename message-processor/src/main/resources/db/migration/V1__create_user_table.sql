create table users(
    id serial,
    email varchar(60),
    username varchar(60),
    token varchar(60),
    is_active integer
);
insert into users(email,username,token,is_active) values ('xxx@yyy.zzz','currency','fair',1);