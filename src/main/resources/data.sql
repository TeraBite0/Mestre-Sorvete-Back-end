INSERT INTO TIPO
        (NOME_TIPO)
VALUES ('Picolé'),
       ('Pote'),
       ('Pote Pequenos'),
       ('Cone'),
       ('Palheta'),
       ('Açai'),
       ('Açai Pequeno');

INSERT INTO SUBTIPO
       (NOME_SUBT, FK_ID_TIPO_PAI)
VALUES ('Extrusado', 1),
       ('Ao leite', 1),
       ('Cremosinho', 1),
       ('Especiais', 1),
       ('Palhetas', 1),
       ('Extrusado sem cobertura', 1),
       ('Torpedinhos', 1),
       ('Infantil', 1),
       ('Pote', 2),
       ('Pote Pequenos', 3),
       ('Cone', 4),
       ('Palheta', 5),
       ('Açai', 6),
       ('Açai Pequeno', 7);

INSERT INTO MARCA
        (NOME_MARCA)
VALUES ('Senhor Sorvete'),
       ('PimPinella'),
       ('Gelone'),
       ('Artegel'),
       ('Cream Coler'),
       ('Fruta Brasileira'),
       ('Sorvete da Chacara'),
       ('Icream'),
       ('Mr Mix');

INSERT INTO PRODUTO
        (NOME_PROD, PRECO_PROD, FK_ID_SUBT_PROD, FK_ID_MARCA_PROD, IS_ATIVO_PROD, EM_ESTOQUE_PROD, TEM_LACTOSE_PROD, TEM_GLUTEM_PROD)
VALUES
    ('Nescolak', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('cookies', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Biscoitos', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('brigadeirissimo', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('maximum: paçoca cremosa', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('premium: chocolate trufado', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('mousse de maracujá', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('black classico', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('premium: frutas vermelha', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('petit gateau', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('gourmet: cocada baiana', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('trufado chocolate', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('torta de limão', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('trufa', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('caramelo', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('bolo de brownie', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('bolo de cenoura', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('torta holandesa', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('torta de limão', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('avelã', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('bem casado', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('petit gateau', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('duo amore', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('brounie com doce de leite', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Morango', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Doce de leite', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Chocolate', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Leite condensado', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Abacaxi suíço', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Mousse de uva', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Mamão com casca', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Limão suíço', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Coco branco', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Coco queimado', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Chiclete', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Banana', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Milho verde', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Leite ninho', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Uva', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Leite condensado', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Limão suíço', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Caramelo', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Leite ninho trufado', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Brigadeiro', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Chocolate black', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Franuito', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Tentação', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Chocoblitz', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('ChocoTella', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Queijo', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Pistache', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Açaí', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Divino', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Skimo', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Delírio', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Brigadeiro com morango', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Chocolate trufado', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Pistache classic', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Banoffe pie', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Torta de limão', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Picolé zero leite', 7.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Niño trufado', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Pistache com chocolate branco', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Morango com leite condensado', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Duete', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Doce de leite com cobertura de chocolate', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('ShowTella', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Krocantissimo', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Creme club hula hula coco', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Grego', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Creme e maracujá', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Açaí com guaraná', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Mashmallow', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Graviola', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Fruta Fa cupuaçu', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Banana caramelada', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Palha cinho', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Amendoim', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Abacate', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Romeu e Julieta', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Churros', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Mousse de maracujá', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Flocos', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Iogurte com frutas vermelhas', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Ameixa', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Açaí', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Abacaxi hortelã', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Mini-saia', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Melancia', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Manga', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Jabuticaba', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Tamarindo', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Maracujá', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Goiaba', 10.00, 1, 1, 'true', 'false', 'false', 'false'),
    ('Banoffe', 10.00, 4, 1, 'true', 'false', 'false', 'false'),
    ('Chocolate + Baunilha', 10.00, 4, 1, 'true', 'false', 'false', 'false'),
    ('Trufado', 10.00, 4, 1, 'true', 'false', 'false', 'false'),
    ('Crocante', 10.00, 4, 1, 'true', 'false', 'false', 'false'),
    ('Niño com creme de avelã', 50.00, 2, 1, 'true', 'false', 'false', 'false'),
    ('Niño com morango', 50.00, 2, 1, 'true', 'false', 'false', 'false'),
    ('Niño trufado', 50.00, 2, 1, 'true', 'false', 'false', 'false'),
    ('Ninho com doce de leite', 50.00, 2, 1, 'true', 'false', 'false', 'false'),
    ('Rafaelo', 50.00, 2, 1, 'true', 'false', 'false', 'false'),
    ('Kinder', 50.00, 2, 1, 'true', 'false', 'false', 'false'),
    ('Pavê italiano', 50.00, 2, 1, 'true', 'false', 'false', 'false'),
    ('Doce de leite folhado', 50.00, 2, 1, 'true', 'false', 'false', 'false'),
    ('Iogurte grego com morango', 50.00, 2, 1, 'true', 'false', 'false', 'false'),
    ('Napolitano', 50.00, 3, 1, 'true', 'false', 'false', 'false'),
    ('Açaí Pequeno', 50.00, 7, 1, 'true', 'false', 'false', 'false'),
    ('Açaí Grande', 50.00, 6, 1, 'true', 'false', 'false', 'false'),
    ('Açaí com ninho', 50.00, 6, 1, 'true', 'false', 'false', 'false'),
    ('Açaí com nutela', 50.00, 6, 1, 'true', 'false', 'false', 'false');

INSERT INTO USUARIO
        (EMAIL_USUARIO, SENHA_USUARIO)
VALUES ('josue@gmail.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC');



INSERT INTO PERDA
        (FK_ID_PROD_PERD, DT_PERDA_PERD, QTD_PRODUTO_PERD)
VALUES  (2, '2024-10-05', 2),
        (5, '2024-09-15', 1),
        (6, '2024-05-13', 5),
        (9, '2024-07-26', 4),
        (30, '2024-06-30', 2);
/*
    Comentar de volta quando terminar
*/
INSERT INTO VENDAS
        (DATA_COMPRA_VEND)
VALUES  ('2024-10-13T15:45:20'),
        ('2024-12-06T15:45:20'),
        ('2024-12-06T15:50:20'),
        ('2025-02-08'),
        ('2025-02-09'),
        ('2025-02-10'),
        ('2025-02-02'),
        ('2025-02-01');

INSERT INTO VENDAS_PRODUTO
        (FK_ID_VEND_VENP, FK_ID_PROD_VENP, QTD_PRODUTOS_VENDIDO)
VALUES  (1, 2, 30),
        (1, 3, 20),
        (2, 6, 40),
        (2, 20, 10),
        (3, 9, 34),
        (6, 62, 10),
        (5, 78, 5),
        (5, 90, 10),
        (7, 3, 20);

INSERT INTO NOTIFICACAO
       (EMAIL_NOTI, FK_ID_PROD_NOTI)
VALUES ('bruno@gmail.com', 20),
       ('guilherme@gmail.com', 35),
       ('gus@gmail.com', 15),
       ('kauan@gmail.com', 11),
       ('thamires@gmail.com', 19),
       ('maria@gmail.com', 56);

INSERT INTO LOTE
    (DATA_PEDIDO_LOTE, DATA_ENTREGA_LOTE, DATA_VENCIMENTO_LOTE, QTD_PRODUTO_COMPRADO_LOTE, VALOR_LOTE, FK_ID_PROD_LOTE)
VALUES  ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 1),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 2),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 3),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 4),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 5),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 6),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 7),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 8),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 9),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 10),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 12),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 13),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 14),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 16),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 17),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 18),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 20),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 21),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 22),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 24),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 25),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 26),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 28),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 29),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 30),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 32),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 33),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 34),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 36),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 37),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 39),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 40),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 81),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 93),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 66),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 46),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 47),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 48),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 49),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 42),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 92),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 80),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 63),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 62),
        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 110);

