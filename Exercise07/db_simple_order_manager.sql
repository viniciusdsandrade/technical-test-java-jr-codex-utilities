# 7) Entity Relationship Diagram - Simple Order Manager
# Design the model of a simple Order Manager System.
# The system consists of:
# Clients
# Products
# Orders
# Any other tables you may need
# You can draw, describe, or list the tables as SQL.
# Extras:
# SQL: list ORDERS with number of items
# Which indexes should be created in this model?
# Note: this exercise is documentation only - there's no executable to run in this case.

DROP DATABASE IF EXISTS db_simple_order_manager;
CREATE DATABASE IF NOT EXISTS db_simple_order_manager;
USE db_simple_order_manager;

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS tb_clients
(
    id      BIGINT UNSIGNED AUTO_INCREMENT,
    name    VARCHAR(100)        NOT NULL,
    email   VARCHAR(100) UNIQUE NOT NULL,
    phone   VARCHAR(15)         NULL,
    address VARCHAR(255)        NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS tb_products
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    name           VARCHAR(100)   NOT NULL,
    description    TEXT           NULL,
    price          DECIMAL(10, 2) NOT NULL,
    stock_quantity INT UNSIGNED   NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

-- Tabela de Pedidos
CREATE TABLE IF NOT EXISTS tb_orders
(
    id           BIGINT UNSIGNED AUTO_INCREMENT,
    client_id    BIGINT UNSIGNED                                      NOT NULL,
    order_date   DATE                                                 NOT NULL,
    status       ENUM ('pending', 'shipped', 'delivered', 'canceled') NOT NULL,
    total_amount DECIMAL(10, 2) DEFAULT 0.00                          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES tb_clients (id)
);

-- Tabela de Itens do Pedido
CREATE TABLE IF NOT EXISTS tb_order_items
(
    id         BIGINT UNSIGNED AUTO_INCREMENT,
    order_id   BIGINT UNSIGNED             NOT NULL,
    product_id BIGINT UNSIGNED             NOT NULL,
    quantity   INT UNSIGNED                NOT NULL,
    price      DECIMAL(10, 2) DEFAULT 0.00 NOT NULL, -- Subtotal calculado pelo trigger
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES tb_orders (id),
    FOREIGN KEY (product_id) REFERENCES tb_products (id)
);

-- Tabela de Pagamentos
CREATE TABLE IF NOT EXISTS tb_payments
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    order_id       BIGINT UNSIGNED                                         NOT NULL,
    payment_date   DATE                                                    NOT NULL,
    amount         DECIMAL(10, 2) DEFAULT 0.00                             NOT NULL, -- Valor calculado pelo trigger
    payment_method ENUM ('credit_card', 'paypal', 'bank_transfer', 'cash') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES tb_orders (id)
);

-- ######################################################################
-- Índice: idx_orders_client_id
-- Justificativa: Este índice acelera consultas que envolvem a busca de pedidos
-- por cliente. A coluna `client_id` será frequentemente usada em junções entre
-- `tb_clients` e `tb_orders` para listar os pedidos de um determinado cliente,
-- por isso o índice melhora o desempenho dessas consultas.
-- ######################################################################
CREATE INDEX idx_orders_client_id ON tb_orders (client_id);

-- ######################################################################
-- Índice: idx_order_items_order_id
-- Justificativa: Este índice é importante para acelerar consultas que buscam
-- itens de pedido com base no ID do pedido (`order_id`), que é a chave estrangeira
-- para `tb_orders`. Sempre que houver consultas listando os itens de um pedido,
-- este índice tornará a operação muito mais eficiente.
-- ######################################################################
CREATE INDEX idx_order_items_order_id ON tb_order_items (order_id);

-- ######################################################################
-- Índice: idx_order_items_product_id
-- Justificativa: Este índice acelera consultas relacionadas a itens de pedido
-- com base no ID do produto (`product_id`). Ele é útil para operações que precisam
-- listar todos os pedidos de um determinado produto, facilitando relatórios
-- e consultas envolvendo produtos específicos.
-- ######################################################################
CREATE INDEX idx_order_items_product_id ON tb_order_items (product_id);

-- ######################################################################
-- Índice: idx_payments_order_id
-- Justificativa: O índice na coluna `order_id` da tabela `tb_payments` melhora
-- o desempenho das consultas que relacionam pagamentos aos pedidos. Isso é útil
-- para rapidamente encontrar o pagamento de um determinado pedido ou listar todos
-- os pagamentos feitos para um pedido.
-- ######################################################################
CREATE INDEX idx_payments_order_id ON tb_payments (order_id);

-- ######################################################################
-- Índice: idx_clients_email
-- Justificativa: O índice único na coluna `email` da tabela `tb_clients` assegura
-- que os e-mails sejam únicos e acelera consultas que buscam clientes pelo campo
-- de e-mail, o que é uma operação comum, já que o e-mail é muitas vezes usado como
-- identificador principal de clientes no sistema.
-- ######################################################################
CREATE UNIQUE INDEX idx_clients_email ON tb_clients (email);

