INSERT INTO TIPO
        (NOME_TIPO)
VALUES ('Picolé'),
       ('Pote'),
       ('Pote Pequenos'),
       ('Cone'),
       ('Palheta'),
       ('Açai'),
       ('Açai Pequeno');

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
        (NOME_PROD, PRECO_PROD, QDT_ESTOQUE_PROD, FK_ID_TIPO, FK_ID_MARCA_PROD, IS_ATIVO_PROD)
VALUES  ('Nescolak', 10.00, 35, 1, 1, 'true'),
        ('cookies', 10.00, 42, 1, 1, 'true'),
        ('Biscoitos', 10.00, 18, 1, 1, 'true'),
        ('brigadeirissimo', 10.00, 50, 1, 1, 'true'),
        ('maximum: paçoca cremosa', 10.00, 27, 1, 1, 'true'),
        ('premium: chocolate trufado', 10.00, 65, 1, 1, 'true'),
        ('mousse de maracujá', 10.00, 22, 1, 1, 'true'),
        ('black classico', 10.00, 10, 1, 1, 'true'),
        ('premium: frutas vermelha', 10.00, 44, 1, 1, 'true'),
        ('petit gateau', 10.00, 12, 1, 1, 'true'),
        ('gourmet: cocada baiana', 10.00, 31, 1, 1, 'true'),
        ('trufado chocolate', 10.00, 56, 1, 1, 'true'),
        ('torta de limão', 10.00, 29, 1, 1, 'true'),
        ('trufa', 10.00, 48, 1, 1, 'true'),
        ('caramelo', 10.00, 13, 1, 1, 'true'),
        ('bolo de brownie', 10.00, 37, 1, 1, 'true'),
        ('bolo de cenoura', 10.00, 25, 1, 1, 'true'),
        ('torta holandesa', 10.00, 60, 1, 1, 'true'),
        ('torta de limão', 10.00, 52, 1, 1, 'true'),
        ('avelã', 10.00, 40, 1, 1, 'true'),
        ('bem casado', 10.00, 15, 1, 1, 'true'),
        ('petit gateau', 10.00, 28, 1, 1, 'true'),
        ('duo amore', 10.00, 20, 1, 1, 'true'),
        ('brounie com doce de leite', 10.00, 47, 1, 1, 'true'),
        ('Morango', 7.00, 33, 1, 1, 'true'),
        ('Doce de leite', 7.00, 22, 1, 1, 'true'),
        ('Chocolate', 7.00, 41, 1, 1, 'true'),
        ('Leite condensado', 7.00, 38, 1, 1, 'true'),
        ('Abacaxi suíço', 7.00, 25, 1, 1, 'true'),
        ('Mousse de uva', 7.00, 47, 1, 1, 'true'),
        ('Mamão com casca', 7.00, 29, 1, 1, 'true'),
        ('Limão suíço', 7.00, 32, 1, 1, 'true'),
        ('Coco branco', 7.00, 40, 1, 1, 'true'),
        ('Coco queimado', 7.00, 21, 1, 1, 'true'),
        ('Chiclete', 7.00, 45, 1, 1, 'true'),
        ('Banana', 7.00, 36, 1, 1, 'true'),
        ('Milho verde', 7.00, 28, 1, 1, 'true'),
        ('Leite ninho', 7.00, 42, 1, 1, 'true'),
        ('Uva', 7.00, 33, 1, 1, 'true'),
        ('Leite condensado', 7.00, 30, 1, 1, 'true'),
        ('Limão suíço', 7.00, 22, 1, 1, 'true'),
        ('Caramelo', 7.00, 25, 1, 1, 'true'),
        ('Leite ninho trufado', 7.00, 37, 1, 1, 'true'),
        ('Brigadeiro', 7.00, 46, 1, 1, 'true'),
        ('Chocolate black', 7.00, 41, 1, 1, 'true'),
        ('Franuito', 7.00, 23, 1, 1, 'true'),
        ('Tentação', 7.00, 50, 1, 1, 'true'),
        ('Chocoblitz', 7.00, 29, 1, 1, 'true'),
        ('ChocoTella', 7.00, 48, 1, 1, 'true'),
        ('Queijo', 7.00, 30, 1, 1, 'true'),
        ('Pistache', 7.00, 43, 1, 1, 'true'),
        ('Açaí', 7.00, 36, 1, 1, 'true'),
        ('Divino', 7.00, 27, 1, 1, 'true'),
        ('Skimo', 7.00, 40, 1, 1, 'true'),
        ('Delírio', 7.00, 34, 1, 1, 'true'),
        ('Brigadeiro com morango', 7.00, 39, 1, 1, 'true'),
        ('Chocolate trufado', 7.00, 26, 1, 1, 'true'),
        ('Pistache classic', 7.00, 44, 1, 1, 'true'),
        ('Banoffe pie', 7.00, 35, 1, 1, 'true'),
        ('Torta de limão', 7.00, 21, 1, 1, 'true'),
        ('Picolé zero leite', 7.00, 28, 1, 1, 'true'),
        ('Niño trufado', 10.00, 25, 1, 1, 'true'),
        ('Pistache com chocolate branco', 10.00, 33, 1, 1, 'true'),
        ('Morango com leite condensado', 10.00, 30, 1, 1, 'true'),
        ('Duete', 10.00, 42, 1, 1, 'true'),
        ('Doce de leite com cobertura de chocolate', 10.00, 29, 1, 1, 'true'),
        ('ShowTella', 10.00, 37, 1, 1, 'true'),
        ('Krocantissimo', 10.00, 40, 1, 1, 'true'),
        ('Creme club hula hula coco', 10.00, 28, 1, 1, 'true'),
        ('Grego', 10.00, 36, 1, 1, 'true'),
        ('Creme e maracujá', 10.00, 35, 1, 1, 'true'),
        ('Açaí com guaraná', 10.00, 44, 1, 1, 'true'),
        ('Mashmallow', 10.00, 30, 1, 1, 'true'),
        ('Graviola', 10.00, 27, 1, 1, 'true'),
        ('Fruta Fa cupuaçu', 10.00, 39, 1, 1, 'true'),
        ('Banana caramelada', 10.00, 31, 1, 1, 'true'),
        ('Palha cinho', 10.00, 46, 1, 1, 'true'),
        ('Amendoim', 10.00, 34, 1, 1, 'true'),
        ('Abacate', 10.00, 28, 1, 1, 'true'),
        ('Romeu e Julieta', 10.00, 50, 1, 1, 'true'),
        ('Churros', 10.00, 45, 1, 1, 'true'),
        ('Mousse de maracujá', 10.00, 26, 1, 1, 'true'),
        ('Flocos', 10.00, 22, 1, 1, 'true'),
        ('Iogurte com frutas vermelhas', 10.00, 40, 1, 1, 'true'),
        ('Ameixa', 10.00, 38, 1, 1, 'true'),
        ('Açaí', 10.00, 29, 1, 1, 'true'),
        ('Abacaxi hortelã', 10.00, 32, 1, 1, 'true'),
        ('Mini-saia', 10.00, 21, 1, 1, 'true'),
        ('Melancia', 10.00, 47, 1, 1, 'true'),
        ('Manga', 10.00, 30, 1, 1, 'true'),
        ('Jabuticaba', 10.00, 33, 1, 1, 'true'),
        ('Tamarindo', 10.00, 44, 1, 1, 'true'),
        ('Maracujá', 10.00, 31, 1, 1, 'true'),
        ('Goiaba', 10.00, 29, 1, 1, 'true'),
        ('Banoffe', 10.00, 52, 4, 1, 'true'),
        ('Chocolate + Baunilha', 10.00, 30, 4, 1, 'true'),
        ('Trufado', 10.00, 18, 4, 1, 'true'),
        ('Crocante', 10.00, 45, 4, 1, 'true'),
        ('Niño com creme de avelã', 50.00, 35, 2, 1, 'true'),
        ('Niño com morango', 50.00, 40, 2, 1, 'true'),
        ('Niño trufado', 50.00, 25, 2, 1, 'true'),
        ('Ninho com doce de leite', 50.00, 55, 2, 1, 'true'),
        ('Rafaelo', 50.00, 30, 2, 1, 'true'),
        ('Kinder', 50.00, 20, 2, 1, 'true'),
        ('Pavê italiano', 50.00, 42, 2, 1, 'true'),
        ('Doce de leite folhado', 50.00, 37, 2, 1, 'true'),
        ('Iogurte grego com morango', 50.00, 29, 2, 1, 'true'),
        ('Napolitano', 50.00, 50, 3, 1, 'true'),
        ('Açaí Pequeno', 50.00, 50, 7, 1, 'true'),
        ('Açaí Grande', 50.00, 50, 6, 1, 'true'),
        ('Açaí com ninho', 50.00, 33, 6, 1, 'true'),
        ('Açaí com nutela', 50.00, 47, 6, 1, 'true');

INSERT INTO PERDA
        (FK_ID_PROD_PERD, DT_PERDA_PERD, QTD_PRODUTO_PERD)
VALUES  (2, '2024-10-05', 2),
        (5, '2024-09-15', 1),
        (6, '2024-05-13', 5),
        (9, '2024-07-26', 4),
        (30, '2024-06-30', 2);