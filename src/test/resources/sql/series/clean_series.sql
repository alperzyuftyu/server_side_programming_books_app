delete  from book;
alter table book alter column id restart with 1;
delete from  serie;
alter table serie alter column id restart with 1;