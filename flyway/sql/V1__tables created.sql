create schema if not exists freelance;

create table if not exists public.person(
person_id serial primary key,
    person_id integer  primary key null,
    name      varchar(100) unique,
    password  varchar(100)        not null,
role varchar(100) not null,
    rating    integer             not null,
    email     varchar(100)        not null unique

);

create table if not exists freelance.skill(
skill_id serial primary key,
    name     varchar(100)        not null unique
);

--CREATE TYPE public.skill_level AS ENUM ('bad', 'semi_good', 'good', 'semi_profi', 'profi');

create table if not exists freelance.person_skill(
person_skill_id serial primary key,
level varchar(100) not null,
    person_id       integer              not null,
    skill_id        integer              not null,
    foreign key (person_id) references freelance.person (person_id),
    foreign key (skill_id) references freelance.skill (skill_id)
);

--CREATE TYPE public.task_status AS ENUM ('in_design', 'publish', 'assigned', 'in_work', 'done');

create table if not exists freelance.task(
task_id serial primary key ,
    name         varchar(100)        not null,
    description  varchar(100),
    deadline     timestamp,
status varchar(100) not null,
    performer_id integer ,
    author_id    integer              not null,
    foreign key (performer_id) references freelance.person (person_id),
    foreign key (author_id) references freelance.person (person_id)
);

create table if not exists freelance.review(
review_id serial primary key,
    description varchar(100),
    datetime    timestamp,
    task_id     integer              not null,
    person_id   integer              not null,
    foreign key (person_id) references freelance.person (person_id),
    foreign key (task_id) references freelance.task (task_id)
);


CREATE TYPE freelance.message_status AS ENUM ('inbox', 'outbox');

create table if not exists public.candidate(
candidate_id serial primary key  ,
task_id integer not null,
person_id integer not null,
foreign key(person_id) references public.person(person_id),
foreign key(task_id) references public.task(task_id)
);

create table if not exists freelance.candidate
(
task_skill_id serial primary key,
level varchar(100) not null,
skill_id integer not null,
    foreign key (person_id) references freelance.person (person_id),
    foreign key (task_id) references freelance.task (task_id)
);

create table if not exists freelance.task_skill
(
    task_skill_id integer  primary key null,
    level         skill_level         not null,
    task_id       integer              not null,
    skill_id      integer              not null,
    foreign key (task_id) references freelance.task (task_id),
    foreign key (skill_id) references freelance.skill (skill_id)
);
//values (3, 'test', 'qwerty', 'user', 1, 'example@ru.com')