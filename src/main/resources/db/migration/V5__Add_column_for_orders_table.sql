alter table orders
add column create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP after user_id,
add column update_time TIMESTAMP ON UPDATE CURRENT_TIMESTAMP after create_time