CREATE TYPE public.user_role AS ENUM ('admin', 'user');

create table if not exists public.person(
person_id serial not null primary key,
name varchar(100) not null unique,
password varchar(100) not null,
role user_role not null,
rating integer not null,
email varchar(100) not null unique
);


create table if not exists public.skills(
skill_id serial not null primary key,
name varchar(100) not null unique
);

CREATE TYPE public.skill_level AS ENUM ('bad', 'semi_good', 'good', 'semi_profi', 'profi');


create table if not exists public.user_skills(
usr_skill_id serial not null primary key,
level skill_level,
person_id serial,
skill_id serial unique,
foreign key(person_id) references public.person(person_id),
foreign key(skill_id) references public.skills(skill_id)
);

CREATE TYPE public.task_status AS ENUM ('preparing', 'publish', 'assigned', 'in_work', 'done');

create table if not exists public.tasks(
task_id serial not null primary key,
name varchar(100) not null,
description varchar(100),
deadline timestamp,
status task_status not null,
assigned_user serial,
skills serial unique,
author_id serial,
foreign key(assigned_user) references public.person(person_id),
foreign key(skills) references public.skills(skill_id),
foreign key(author_id) references public.person(person_id)
);

create table if not exists public.reviews(
review_id serial not null primary key,
description varchar(100),
datetime timestamp,
task_id serial,
user_id serial,
foreign key(user_id) references public.person(person_id),
foreign key(task_id) references public.tasks(task_id)
);


CREATE TYPE public.message_status AS ENUM ('inbox', 'outbox');

create table if not exists public.message(
msg_id serial not null primary key,
message varchar(100),
datetime timestamp,
status message_status not null,
sender_id serial,
receiver_id serial,
task_id serial,
foreign key(sender_id) references public.person(person_id),
foreign key(receiver_id) references public.person(person_id),
foreign key(task_id) references public.tasks(task_id)
);

alter table public.person add column review_id serial;
ALTER TABLE public.person ADD CONSTRAINT fk_person_reviews FOREIGN KEY (review_id) REFERENCES public.reviews(review_id);

alter table public.person add column task_id serial;
ALTER TABLE public.person ADD CONSTRAINT fk_person_tasks FOREIGN KEY (task_id) REFERENCES public.tasks(task_id);

alter table public.skills add column task_id serial;
ALTER TABLE public.person ADD CONSTRAINT fk_task_skill FOREIGN KEY (task_id) REFERENCES public.tasks(task_id);