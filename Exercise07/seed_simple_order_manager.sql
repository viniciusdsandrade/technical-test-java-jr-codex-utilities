USE db_simple_order_manager;

INSERT INTO tb_clients (name, email, phone, address) VALUES ('John Doe', 'john.doe@example.com', '123-456-7890', '123 Main St, Springfield');
INSERT INTO tb_clients (name, email, phone, address) VALUES ('Jane Smith', 'jane.smith@example.com', '555-123-4567', '456 Elm St, Metropolis');
INSERT INTO tb_clients (name, email, phone, address) VALUES ('Alice Johnson', 'alice.johnson@example.com', '987-654-3210', '789 Oak St, Gotham');

INSERT INTO tb_products (name, description, price, stock_quantity) VALUES ('Laptop', 'High performance laptop', 1500.00, 20);
INSERT INTO tb_products (name, description, price, stock_quantity) VALUES ('Smartphone', 'Latest model smartphone', 800.00, 50);
INSERT INTO tb_products (name, description, price, stock_quantity) VALUES ('Headphones', 'Noise-canceling headphones', 200.00, 100);

INSERT INTO tb_orders (client_id, order_date, status) VALUES (1, '2024-10-01', 'pending');  -- Total será calculado pelo trigger
INSERT INTO tb_orders (client_id, order_date, status) VALUES (2, '2024-10-02', 'shipped');  -- Total será calculado pelo trigger
INSERT INTO tb_orders (client_id, order_date, status) VALUES (3, '2024-10-03', 'delivered');  -- Total será calculado pelo trigger

-- Pedido 1 (id 1)
INSERT INTO tb_order_items (order_id, product_id, quantity) VALUES (1, 1, 1);  -- 1 Laptop
INSERT INTO tb_order_items (order_id, product_id, quantity) VALUES (1, 2, 5);  -- 1 Smartphone

-- Pedido 2 (id 2)
INSERT INTO tb_order_items (order_id, product_id, quantity) VALUES (2, 2, 4);  -- 1 Smartphone

-- Pedido 3 (id 3)
INSERT INTO tb_order_items (order_id, product_id, quantity) VALUES (3, 1, 10);  -- 1 Laptop
INSERT INTO tb_order_items (order_id, product_id, quantity) VALUES (3, 3, 5);  -- 1 Headphones

INSERT INTO tb_payments (order_id, payment_date, payment_method) VALUES (1, '2024-10-01', 'credit_card'); -- Pagamento para o pedido 1 (id 1)
INSERT INTO tb_payments (order_id, payment_date, payment_method) VALUES (2, '2024-10-02', 'paypal'); -- Pagamento para o pedido 2 (id 2)
INSERT INTO tb_payments (order_id, payment_date, payment_method) VALUES (3, '2024-10-03', 'bank_transfer'); -- Pagamento para o pedido 3 (id 3)

SELECT * FROM tb_clients;
SELECT * FROM tb_products;
SELECT * FROM tb_orders;
SELECT * FROM tb_order_items;
SELECT * FROM tb_payments;
SELECT * FROM vw_orders_with_item_count;

