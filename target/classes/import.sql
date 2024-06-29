INSERT INTO TB_CLIENTE (cpf, nome, classe_bonus) VALUES ('11111111111', 'Joana', 'Bronze');
INSERT INTO TB_CLIENTE (cpf, nome, classe_bonus) VALUES ('22222222222', 'Carlos', 'Prata');
INSERT INTO TB_CLIENTE (cpf, nome, classe_bonus) VALUES ('33333333333', 'Marcos', 'Ouro');
INSERT INTO TB_PRODUTO (descricao, preco, dt_validade) VALUES ('Enxaguante Bucal Sem Álcool Listerine Cool Mint 1L', 29.99, '2026-06-30');
INSERT INTO TB_PRODUTO (descricao, preco, dt_validade) VALUES ('Dipirona Sódica 500mg/ml Solução Gotas 20ml', 4.29, '2024-06-30');
INSERT INTO TB_PRODUTO (descricao, preco, dt_validade) VALUES ('Creme Hidratante Corporal Pele Seca 453g', 69.50, '2023-12-31');
INSERT INTO TB_ITEM_COMPRA (produto_id, quantidade, sub_total) VALUES (1, 2, 59.98);
INSERT INTO TB_ITEM_COMPRA (produto_id, quantidade, sub_total) VALUES (2, 5, 21.45);
INSERT INTO TB_ITEM_COMPRA (produto_id, quantidade, sub_total) VALUES (3, 1, 69.50);
INSERT INTO TB_CARRINHO_COMPRA (cliente_id, total, desconto, total_com_desconto) VALUES (1, 150.93, 10.00, 140.93);
INSERT INTO TB_CARRINHO_COMPRA (cliente_id, total, desconto, total_com_desconto) VALUES (2, 59.98, 5.00, 54.98);
UPDATE TB_ITEM_COMPRA SET carrinho_compra_id = 1 WHERE id IN (1, 2);
UPDATE TB_ITEM_COMPRA SET carrinho_compra_id = 2 WHERE id = 3;

