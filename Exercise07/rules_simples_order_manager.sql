USE db_simple_order_manager;

DROP VIEW IF EXISTS vw_orders_with_item_count;
DROP FUNCTION IF EXISTS fun_check_stock;
DROP TRIGGER IF EXISTS tg_calculate_item_price;
DROP TRIGGER IF EXISTS tg_update_order_total;
DROP TRIGGER IF EXISTS tg_calculate_payment_amount;
DROP TRIGGER IF EXISTS tg_update_stock_after_payment;
DROP TRIGGER IF EXISTS tg_check_stock_before_insert;

-- ######################################################################
-- Trigger: calculate_item_price
-- Resumo: Este trigger calcula o preço total de cada item de pedido (subtotal)
-- antes de inserir o registro na tabela 'tb_order_items'.
-- O preço é calculado multiplicando o preço do produto pela quantidade comprada.
-- ######################################################################

DELIMITER //

CREATE TRIGGER IF NOT EXISTS tg_calculate_item_price
    BEFORE INSERT
    ON tb_order_items
    FOR EACH ROW
BEGIN
    -- Calcula o subtotal automaticamente baseado no preço do produto e quantidade
    SET NEW.price = (SELECT price FROM tb_products WHERE id = NEW.product_id) * NEW.quantity;
END;

//

DELIMITER ;

-- ######################################################################
-- Trigger: tg_update_order_total
-- Resumo: Após a inserção de um item na tabela 'tb_order_items', este trigger
-- atualiza o valor total do pedido na tabela 'tb_orders', somando o preço de
-- todos os itens do pedido.
-- ######################################################################

DELIMITER //

CREATE TRIGGER IF NOT EXISTS tg_update_order_total
    AFTER INSERT
    ON tb_order_items
    FOR EACH ROW
BEGIN
    -- Atualiza o total do pedido após a inserção de um novo item
    UPDATE tb_orders
    SET total_amount = (SELECT SUM(price)
                        FROM tb_order_items
                        WHERE order_id = NEW.order_id)
    WHERE id = NEW.order_id;
END;

//
DELIMITER ;

-- ######################################################################
-- Trigger: tg_calculate_payment_amount
-- Resumo: Antes da inserção de um pagamento na tabela 'tb_payments',
-- este trigger preenche o campo 'amount' com o valor total do pedido correspondente
-- da tabela `tb_orders`.
-- ######################################################################

DELIMITER //

CREATE TRIGGER IF NOT EXISTS tg_calculate_payment_amount
    BEFORE INSERT
    ON tb_payments
    FOR EACH ROW
BEGIN
    -- Preenche o campo `amount` com o total do pedido
    SET NEW.amount = (SELECT total_amount FROM tb_orders WHERE id = NEW.order_id);
END;

//
DELIMITER ;

-- ######################################################################
-- Trigger: tg_update_stock_after_payment
-- Resumo: Este trigger é acionado após a inserção de um registro de pagamento
-- na tabela 'tb_payments'. Ele atualiza o estoque de todos os produtos
-- comprados no pedido correspondente, diminuindo as quantidades conforme a
-- compra registrada na tabela 'tb_order_items`.
-- ######################################################################

DELIMITER $$

CREATE TRIGGER IF NOT EXISTS tg_update_stock_after_payment
    AFTER INSERT
    ON tb_payments -- O trigger será executado após uma inserção em 'tb_payments'
    FOR EACH ROW
BEGIN
    -- Variável para controlar o fim do cursor
    DECLARE done INT DEFAULT 0;

    -- Variáveis para armazenar os valores do cursor durante a iteração
    DECLARE v_product_id BIGINT; -- ID do produto na tabela `tb_order_items`
    DECLARE v_quantity INT;
    -- Quantidade comprada do produto

    -- Declaração do cursor que busca o ID do produto e a quantidade
    -- de todos os itens do pedido relacionado ao pagamento
    DECLARE cur CURSOR FOR
        SELECT product_id, quantity
        FROM tb_order_items
        WHERE order_id = NEW.order_id;
    -- Relaciona os itens ao pedido pago

    -- Handler para definir a variável 'done' como 1 quando não houver mais
    -- registros a serem processados pelo cursor
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    -- Abre o cursor para começar a iterar sobre os itens do pedido
    OPEN cur;

    -- Loop para iterar sobre todos os itens do pedido e atualizar o estoque
    read_loop:
    LOOP
        -- Recupera o próximo `product_id` e `quantity` do cursor
        FETCH cur INTO v_product_id, v_quantity;

        -- Verifica se não há mais registros e sai do loop
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- Atualiza o estoque do produto, subtraindo a quantidade comprada
        UPDATE tb_products
        SET stock_quantity = stock_quantity - v_quantity
        WHERE id = v_product_id; -- Atualiza o estoque do produto com o ID correspondente

    END LOOP;
    -- Fim do loop que itera pelos itens do pedido

    -- Fecha o cursor após completar a iteração
    CLOSE cur;
END $$

DELIMITER ;
-- ######################################################################
-- Function: fun_check_stock
-- Resumo: Esta função verifica se há estoque suficiente para um determinado
-- produto antes de inserir o item de pedido. Ela retorna 'TRUE’ se houver estoque
-- suficiente e 'FALSE' se não houver.
-- ######################################################################

DELIMITER //

CREATE FUNCTION IF NOT EXISTS fun_check_stock(productID BIGINT UNSIGNED, qty INT UNSIGNED)
    RETURNS BOOLEAN
    DETERMINISTIC
BEGIN
    -- Declaração da variável que armazenará a quantidade em estoque
    DECLARE stock INT;

    -- Recupera o estoque atual do produto
    SELECT stock_quantity
    INTO stock
    FROM tb_products
    WHERE id = productID;

    -- Verifica se a quantidade em estoque é suficiente
    IF stock >= qty THEN
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END;

//
DELIMITER ;


-- ######################################################################
-- Trigger: tg_check_stock_before_insert
-- Resumo: Antes de inserir um item de pedido na tabela 'tb_order_items', este trigger
-- verifica se há estoque suficiente para o produto. Se o estoque for insuficiente,
-- a inserção é cancelada e uma mensagem de erro é exibida.
-- ######################################################################

DELIMITER //

CREATE TRIGGER IF NOT EXISTS tg_check_stock_before_insert
    BEFORE INSERT
    ON tb_order_items
    FOR EACH ROW
BEGIN
    -- Verifica se há estoque suficiente para o produto
    IF NOT fun_check_stock(NEW.product_id, NEW.quantity) THEN
        -- Se não houver estoque suficiente, lança um erro e cancela a inserção
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Estoque insuficiente para o produto!';
    END IF;
END;

//
DELIMITER ;


-- ######################################################################
-- View: vw_orders_with_item_count
-- Resumo: Esta view exibe informações sobre os pedidos ('tb_orders'), incluindo
-- o número de itens associados a cada pedido. A view retorna o ID do pedido, a
-- quantidade de itens, o valor total, a data do pedido e o status atual.
-- ######################################################################

CREATE VIEW vw_orders_with_item_count AS
SELECT o.id         AS order_id,        -- Seleciona o ID do pedido da tabela `tb_orders` e o renomeia para `order_id`
       COUNT(oi.id) AS number_of_items, -- Conta o número de itens no pedido, agrupando-os por `order_id`
       o.total_amount,                  -- Seleciona o valor total do pedido da tabela `tb_orders`
       o.order_date,                    -- Seleciona a data do pedido da tabela `tb_orders`
       o.status                         -- Seleciona o status atual do pedido da tabela `tb_orders`
FROM tb_orders o -- Tabela principal `tb_orders`
         JOIN
     tb_order_items oi ON o.id = oi.order_id -- Junção com a tabela 'tb_order_items' para relacionar os pedidos aos seus itens
GROUP BY o.id; -- Agrupa os resultados por 'order_id' para garantir que cada pedido apareça uma vez

