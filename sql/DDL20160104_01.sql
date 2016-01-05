
alter table user change column mobile_phone user_account varchar(64);
alter table user add column account_type varchar(16) NOT NULL DEFAULT '1' after user_account;

drop index IX_USERMP on user;
create unique index IX_USER_ACCPUNT on user(user_account, account_type);