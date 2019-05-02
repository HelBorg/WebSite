drop schema public cascade;
create schema public;

create table if not exists public.person(
person_id serial primary key,
name varchar(100) unique,
password varchar(100) not null,
role varchar(100) not null,
rating integer not null,
email varchar(100) not null unique
);

create table if not exists public.skill(
skill_id serial primary key,
name varchar(100) not null unique
);


create table if not exists public.person_skill(
person_skill_id serial primary key,
level varchar(100) not null,
person_id integer not null,
skill_id integer not null,
foreign key (person_id) references public.person(person_id),
foreign key (skill_id) references public.skill(skill_id)
);

create table if not exists public.task(
task_id serial primary key ,
name varchar(100) not null,
description varchar(100),
deadline timestamp,
time_created timestamp,
status varchar(100) not null,
performer_id integer ,
author_id integer not null,
foreign key (performer_id) references public.person(person_id),
foreign key (author_id) references public.person(person_id)
);

create table if not exists public.review(
review_id serial primary key,
description varchar(100),
datetime timestamp,
task_id integer not null,
person_id integer not null,
foreign key (person_id) references public.person(person_id),
foreign key (task_id) references public.task(task_id)
);


create table if not exists public.candidate(
candidate_id serial primary key,
task_id integer not null,
person_id integer not null,
foreign key(person_id) references public.person(person_id),
foreign key(task_id) references public.task(task_id)
);

create table if not exists public.candidate(
task_skill_id serial primary key,
level varchar(100) not null,
skill_id integer not null,
foreign key (person_id) references public.person(person_id),
foreign key (task_id) references public.task(task_id)
);

create table if not exists public.task_skill(
task_skill_id integer  primary key null,
level varchar(100) not null,
task_id integer not null,
skill_id integer not null,
foreign key (task_id) references public.task (task_id),
foreign key (skill_id) references public.skill (skill_id)
);

insert into skill(name) values('java');
insert into skill(name) values('sql');
insert into skill(name) values('Javascript');
insert into skill(name) values('vue.js');
insert into skill(name) values('spring');
insert into skill(name) values('pl/sql');