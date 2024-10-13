INSERT INTO TIPO
        (NOME_TIPO)
VALUES ('Extrusado'),
       ('Ao leite'),
       ('Cremosinho'),
       ('Especiais'),
       ('Palhetas'),
       ('Extrusado sem cobertura'),
       ('Torpedinhos'),
       ('Infantil');

INSERT INTO SUBTIPO
       (NOME_SUBT, FK_ID_TIPO_PAI)
VALUES ('Picolé', 1),
       ('Pote', null),
       ('Pote Pequenos', null),
       ('Cone', null),
       ('Palheta', null),
       ('Açai', null),
       ('Açai Pequeno', null);

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
        (NOME_PROD, PRECO_PROD, FK_ID_SUBT, FK_ID_MARCA_PROD, IS_ATIVO_PROD, EM_ESTOQUE_PROD)
VALUES  ('Nescolak', 10.00, 1, 1, 'true', 'true'),
        ('cookies', 10.00, 1, 1, 'true', 'true'),
        ('Biscoitos', 10.00, 1, 1, 'true', 'true'),
        ('brigadeirissimo', 10.00, 1, 1, 'true', 'true'),
        ('maximum: paçoca cremosa', 10.00, 1, 1, 'true', 'true'),
        ('premium: chocolate trufado', 10.00, 1, 1, 'true', 'true'),
        ('mousse de maracujá', 10.00, 1, 1, 'true', 'true'),
        ('black classico', 10.00, 1, 1, 'true', 'true'),
        ('premium: frutas vermelha', 10.00, 1, 1, 'true', 'true'),
        ('petit gateau', 10.00, 1, 1, 'true', 'true'),
        ('gourmet: cocada baiana', 10.00, 1, 1, 'true', 'true'),
        ('trufado chocolate', 10.00, 1, 1, 'true', 'true'),
        ('torta de limão', 10.00, 1, 1, 'true', 'true'),
        ('trufa', 10.00, 1, 1, 'true', 'true'),
        ('caramelo', 10.00, 1, 1, 'true', 'true'),
        ('bolo de brownie', 10.00, 1, 1, 'true', 'true'),
        ('bolo de cenoura', 10.00, 1, 1, 'true', 'true'),
        ('torta holandesa', 10.00, 1, 1, 'true', 'true'),
        ('torta de limão', 10.00, 1, 1, 'true', 'true'),
        ('avelã', 10.00, 1, 1, 'true', 'true'),
        ('bem casado', 10.00, 1, 1, 'true', 'true'),
        ('petit gateau', 10.00, 1, 1, 'true', 'true'),
        ('duo amore', 10.00, 1, 1, 'true', 'true'),
        ('brounie com doce de leite', 10.00, 1, 1, 'true', 'true'),
        ('Morango', 7.00, 1, 1, 'true', 'true'),
        ('Doce de leite', 7.00, 1, 1, 'true', 'true'),
        ('Chocolate', 7.00, 1, 1, 'true', 'true'),
        ('Leite condensado', 7.00, 1, 1, 'true', 'true'),
        ('Abacaxi suíço', 7.00, 1, 1, 'true', 'true'),
        ('Mousse de uva', 7.00, 1, 1, 'true', 'true'),
        ('Mamão com casca', 7.00, 1, 1, 'true', 'true'),
        ('Limão suíço', 7.00, 1, 1, 'true', 'true'),
        ('Coco branco', 7.00, 1, 1, 'true', 'true'),
        ('Coco queimado', 7.00, 1, 1, 'true', 'true'),
        ('Chiclete', 7.00, 1, 1, 'true', 'true'),
        ('Banana', 7.00, 1, 1, 'true', 'true'),
        ('Milho verde', 7.00, 1, 1, 'true', 'true'),
        ('Leite ninho', 7.00, 1, 1, 'true', 'true'),
        ('Uva', 7.00, 1, 1, 'true', 'true'),
        ('Leite condensado', 7.00, 1, 1, 'true', 'true'),
        ('Limão suíço', 7.00, 1, 1, 'true', 'true'),
        ('Caramelo', 7.00, 1, 1, 'true', 'true'),
        ('Leite ninho trufado', 7.00, 1, 1, 'true', 'true'),
        ('Brigadeiro', 7.00, 1, 1, 'true', 'true'),
        ('Chocolate black', 7.00, 1, 1, 'true', 'true'),
        ('Franuito', 7.00, 1, 1, 'true', 'true'),
        ('Tentação', 7.00, 1, 1, 'true', 'true'),
        ('Chocoblitz', 7.00, 1, 1, 'true', 'true'),
        ('ChocoTella', 7.00, 1, 1, 'true', 'true'),
        ('Queijo', 7.00, 1, 1, 'true', 'true'),
        ('Pistache', 7.00, 1, 1, 'true', 'true'),
        ('Açaí', 7.00, 1, 1, 'true', 'true'),
        ('Divino', 7.00, 1, 1, 'true', 'true'),
        ('Skimo', 7.00, 1, 1, 'true', 'true'),
        ('Delírio', 7.00, 1, 1, 'true', 'true'),
        ('Brigadeiro com morango', 7.00, 1, 1, 'true', 'true'),
        ('Chocolate trufado', 7.00, 1, 1, 'true', 'true'),
        ('Pistache classic', 7.00, 1, 1, 'true', 'true'),
        ('Banoffe pie', 7.00, 1, 1, 'true', 'true'),
        ('Torta de limão', 7.00, 1, 1, 'true', 'true'),
        ('Picolé zero leite', 7.00, 1, 1, 'true', 'true'),
        ('Niño trufado', 10.00, 1, 1, 'true', 'true'),
        ('Pistache com chocolate branco', 10.00, 1, 1, 'true', 'true'),
        ('Morango com leite condensado', 10.00, 1, 1, 'true', 'true'),
        ('Duete', 10.00, 1, 1, 'true', 'true'),
        ('Doce de leite com cobertura de chocolate', 10.00, 1, 1, 'true', 'true'),
        ('ShowTella', 10.00, 1, 1, 'true', 'true'),
        ('Krocantissimo', 10.00, 1, 1, 'true', 'true'),
        ('Creme club hula hula coco', 10.00, 1, 1, 'true', 'true'),
        ('Grego', 10.00, 1, 1, 'true', 'true'),
        ('Creme e maracujá', 10.00, 1, 1, 'true', 'true'),
        ('Açaí com guaraná', 10.00, 1, 1, 'true', 'true'),
        ('Mashmallow', 10.00, 1, 1, 'true', 'true'),
        ('Graviola', 10.00, 1, 1, 'true', 'true'),
        ('Fruta Fa cupuaçu', 10.00, 1, 1, 'true', 'true'),
        ('Banana caramelada', 10.00, 1, 1, 'true', 'true'),
        ('Palha cinho', 10.00, 1, 1, 'true', 'true'),
        ('Amendoim', 10.00, 1, 1, 'true', 'true'),
        ('Abacate', 10.00, 1, 1, 'true', 'true'),
        ('Romeu e Julieta', 10.00, 1, 1, 'true', 'true'),
        ('Churros', 10.00, 1, 1, 'true', 'true'),
        ('Mousse de maracujá', 10.00, 1, 1, 'true', 'true'),
        ('Flocos', 10.00, 1, 1, 'true', 'true'),
        ('Iogurte com frutas vermelhas', 10.00, 1, 1, 'true', 'true'),
        ('Ameixa', 10.00, 1, 1, 'true', 'true'),
        ('Açaí', 10.00, 1, 1, 'true', 'true'),
        ('Abacaxi hortelã', 10.00, 1, 1, 'true', 'true'),
        ('Mini-saia', 10.00, 1, 1, 'true', 'true'),
        ('Melancia', 10.00, 1, 1, 'true', 'true'),
        ('Manga', 10.00, 1, 1, 'true', 'true'),
        ('Jabuticaba', 10.00, 1, 1, 'true', 'true'),
        ('Tamarindo', 10.00, 1, 1, 'true', 'true'),
        ('Maracujá', 10.00, 1, 1, 'true', 'true'),
        ('Goiaba', 10.00, 1, 1, 'true', 'true'),
        ('Banoffe', 10.00, 4, 1, 'true', 'true'),
        ('Chocolate + Baunilha', 10.00, 4, 1, 'true', 'true'),
        ('Trufado', 10.00, 4, 1, 'true', 'true'),
        ('Crocante', 10.00, 4, 1, 'true', 'true'),
        ('Niño com creme de avelã', 50.00, 2, 1, 'true', 'true'),
        ('Niño com morango', 50.00, 2, 1, 'true', 'true'),
        ('Niño trufado', 50.00, 2, 1, 'true', 'true'),
        ('Ninho com doce de leite', 50.00, 2, 1, 'true', 'true'),
        ('Rafaelo', 50.00, 2, 1, 'true', 'true'),
        ('Kinder', 50.00, 2, 1, 'true', 'true'),
        ('Pavê italiano', 50.00, 2, 1, 'true', 'true'),
        ('Doce de leite folhado', 50.00, 2, 1, 'true', 'true'),
        ('Iogurte grego com morango', 50.00, 2, 1, 'true', 'true'),
        ('Napolitano', 50.00, 3, 1, 'true', 'true'),
        ('Açaí Pequeno', 50.00, 7, 1, 'true', 'true'),
        ('Açaí Grande', 50.00, 6, 1, 'true', 'true'),
        ('Açaí com ninho', 50.00, 6, 1, 'true', 'true'),
        ('Açaí com nutela', 50.00, 6, 1, 'true', 'true');

INSERT INTO PERDA
        (FK_ID_PROD_PERD, DT_PERDA_PERD, QTD_PRODUTO_PERD)
VALUES  (2, '2024-10-05', 2),
        (5, '2024-09-15', 1),
        (6, '2024-05-13', 5),
        (9, '2024-07-26', 4),
        (30, '2024-06-30', 2);

INSERT INTO USUARIO
        (EMAIL_USUARIO, SENHA_USUARIO)
VALUES ('josue@gmail.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC');

INSERT INTO VENDAS
        (DATA_COMPRA_VEND)
VALUES  ('2024-10-13'),
        ('2024-10-05'),
        ('2024-10-06'),
        ('2024-10-08'),
        ('2024-10-09'),
        ('2024-10-10'),
        ('2024-10-02'),
        ('2024-10-01');

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