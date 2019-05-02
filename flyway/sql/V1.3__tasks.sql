insert into person(person_id, name, password, role, rating, email)
 values (1, 'testww', 'test', 'user', 1, 'test@ru.com');

  insert into task(task_id, name, status, author_id)
  values (1, 'hhh', 'read', '1');
  insert into task(task_id, name, status, author_id)
  values (2, 'hfdfhh', 'read', '1');
  insert into task(task_id, name, status, author_id)
  values (3, 'hhhjj', 'read', '1');
  insert into task(task_id, name, status, author_id)
  values (4, 'hhhll', 'read', '3');
  insert into task(task_id, name, status, author_id)
  values (5, 'hjjjhh', 'read', '3');

 insert into candidate(candidate_id, task_id, person_id)
 values (1, 1, 3);
 insert into candidate(candidate_id, task_id, person_id)
 values (2, 4, 1);

delete from skill;
insert into skill(skill_id, name)
values(1,'Java');
insert into skill(skill_id, name)
values(2,'JavaScript');
insert into skill(skill_id, name)
values(3,'Latin');