
alter table battery_info add column lock_status varchar(32) after voltage;

alter table battery_info add column extension varchar(200) after lock_status;

alter table battery_info change column temperature temperature varchar(30);

alter table  add column extension varchar(200) after lock_status;

create unique index IX_USER_NAME on user(user_name);

create table user_attribute (
	user_id int(11) not null,
	attr_name varchar(32) not null,
	attr_value varchar(50) not null,
	create_time datetime,
	update_time datetime,
	primary key (user_id, attr_name)
);
