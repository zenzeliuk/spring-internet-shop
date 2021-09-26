insert into `shop-spring`.users (id, email, first_name, last_name, login, password, status)
VALUES (1, 'admin@admin.com', 'Зензелюк', 'Максим', 'admin',
        '$2a$12$kVwOAz7KJgq.48TealLGC.5NL.DgMXXkFrO3QhfhgL.X0CzD4zTa6', 'ACTIVE');

insert into `shop-spring`.user_role (user_id, roles)
VALUES (1, 'ADMIN');