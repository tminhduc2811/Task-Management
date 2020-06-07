insert into user (id, username, email, password, full_name, avatar) VALUES (1, 'admin', 'ab@hotmail.net', '$2a$10$YKYS6VR8tmaV31zQv5KTs.eV4vOcTgSrhZdkO33uKkWUSwnmFCSlK', 'TA MINH DUC', null)
insert into project (project_name, project_identifier, description, start_date, end_date, created_at, updated_at, user_id) VALUES ('test project', 'KOTL1', 'test desc', '2020-07-09', '2020-09-11', '2020-07-09', '2020-09-11', 1)
insert into backlog (id, project_identifier) VALUES (1, 'KOTL1')
insert into task_count (id, backlog_id, total) VALUES(1, 1, 0)