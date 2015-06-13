create table messages(
    id serial,
    userid integer,
    currencyfrom varchar(3),
    currencyto varchar(3),
    amountsell decimal,
    amountbuy decimal,
    rate real,
    timeplaced timestamp,
    originatingcountry varchar(2)
);
create index originatingcountry_idx on messages(originatingcountry);
create index currencyfrom_idx on messages(currencyfrom);
create index currencyto_idx on messages(currencyto);
create index userid_idx on messages(userid);