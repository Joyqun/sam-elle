alter table battery add column bind_mobile varchar(32) after status;

ALTER TABLE user_code CHANGE COLUMN mobile_phone TO user_account;
