delete from  author;
alter table author alter column id restart with 1;

delete from author where id = 1;

