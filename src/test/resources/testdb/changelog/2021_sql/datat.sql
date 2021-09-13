--ROLES

INSERT INTO warehouse.u_roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO warehouse.u_roles (id, name) VALUES (2, 'ROLE_MANAGER');
INSERT INTO warehouse.u_roles (id, name) VALUES (3, 'ROLE_CLIENT');

--USERS
INSERT INTO warehouse.users (id, firstname, lastname, email, login, password, created_by, created_date, updated_by, updated_date, status) VALUES ('c10b044d-acec-4b23-bcdb-d60989981fd0', 'Admin', 'Adminov', 'test@email.com', 'admin', 'ZaQd102984', 'I', CURRENT_TIMESTAMP, 'I', CURRENT_TIMESTAMP, 0);
INSERT INTO warehouse.users (id, firstname, lastname, email, login, password, created_by, created_date, updated_by, updated_date, status) VALUES ('ba0fec0e-e9d0-4704-b40d-64e538d5052d', 'Client', 'Clientov', 'client@email.com', 'client', 'Ldnfngc20ns23', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, 0);

--USERS_ROLES
INSERT INTO warehouse.users_roles (user_id, role_id) VALUES ('c10b044d-acec-4b23-bcdb-d60989981fd0', 1);
INSERT INTO warehouse.users_roles (user_id, role_id) VALUES ('c10b044d-acec-4b23-bcdb-d60989981fd0', 2);
INSERT INTO warehouse.users_roles (user_id, role_id) VALUES ('ba0fec0e-e9d0-4704-b40d-64e538d5052d', 3);

COMMIT;