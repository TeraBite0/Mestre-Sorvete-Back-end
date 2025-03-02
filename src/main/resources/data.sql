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
        (PRECO_PROD, FK_ID_SUBT_PROD, FK_ID_MARCA_PROD, IS_ATIVO_PROD, TEM_LACTOSE_PROD, TEM_GLUTEN_PROD, NOME_PROD)
    VALUES
    (10.00, 1, 1, 'true', 'false', 'false', 'Nescolak'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Cookies'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Biscoitos'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Brigadeirissimo'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Maximum: Paçoca Cremosa'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Premium: Chocolate Trufado'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Mousse De Maracuja'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Black Classico'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Premium: Frutas Vermelhas'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Petit Gateau'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Gourmet: Cocada Baiana'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Trufado Chocolate'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Torta De Limao'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Trufa'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Caramelo'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Bolo De Brownie'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Bolo De Cenoura'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Torta Holandesa'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Torta De Limao'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Avela'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Bem Casado'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Petit Gateau'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Duo Amore'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Brounie Com Doce De Leite'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Morango'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Doce De Leite'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Chocolate'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Leite Condensado'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Abacaxi Suico'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Mousse De Uva'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Mamao Com Casca'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Limao Suico'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Coco Branco'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Coco Queimado'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Chiclete'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Banana'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Milho Verde'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Leite Ninho'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Uva'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Leite Condensado'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Limao Suico'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Caramelo'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Leite Ninho Trufado'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Brigadeiro'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Chocolate Black'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Franuito'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Tentacao'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Chocoblitz'),
    (07.00, 1, 1, 'true', 'false', 'false', 'ChocoTella'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Queijo'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Pistache'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Acai'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Divino'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Skimo'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Delirio'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Brigadeiro Com Morango'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Chocolate Trufado'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Pistache Classic'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Banoffe Pie'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Torta De Limao'),
    (07.00, 1, 1, 'true', 'false', 'false', 'Picole Zero Leite'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Nino Trufado'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Pistache Com ChocolateBranco'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Morango Com LeiteCondensado'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Duete'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Doce De Leite Com Cobertura De Chocolate'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Show Tella'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Krocantissimo'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Creme Club Hula Hula Coco'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Grego'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Creme e Maracuja'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Acai Com Guarana'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Mashmallow'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Graviola'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Fruta fa Cupuaçu'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Banana Caramelada'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Palha Cinho'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Amendoim'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Abacate'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Romeu e Julieta'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Churros'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Mousse De Maracuja'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Flocos'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Iogurte Com Frutas Vermelhas'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Ameixa'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Acai'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Abacaxi Hortela'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Mini Saia'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Melancia'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Manga'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Jabuticaba'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Tamarindo'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Maracuja'),
    (10.00, 1, 1, 'true', 'false', 'false', 'Goiaba'),
    (10.00, 4, 1, 'true', 'false', 'false', 'Banoffe'),
    (10.00, 4, 1, 'true', 'false', 'false', 'Chocolate Baunilha'),
    (10.00, 4, 1, 'true', 'false', 'false', 'Trufado'),
    (10.00, 4, 1, 'true', 'false', 'false', 'Crocante'),
    (50.00, 2, 1, 'true', 'false', 'false', 'Nino Com Creme De Avela'),
    (50.00, 2, 1, 'true', 'false', 'false', 'Nino Com Morango'),
    (50.00, 2, 1, 'true', 'false', 'false', 'Nino Trufado'),
    (50.00, 2, 1, 'true', 'false', 'false', 'Ninho Com Doce De Leite'),
    (50.00, 2, 1, 'true', 'false', 'false', 'Rafaelo'),
    (50.00, 2, 1, 'true', 'false', 'false', 'Kinder'),
    (50.00, 2, 1, 'true', 'false', 'false', 'Pave Italiano'),
    (50.00, 2, 1, 'true', 'false', 'false', 'Doce De Leite Folhado'),
    (50.00, 2, 1, 'true', 'false', 'false', 'Iogurte Grego Com Morango'),
    (50.00, 3, 1, 'true', 'false', 'false', 'Napolitano'),
    (50.00, 7, 1, 'true', 'false', 'false', 'Acai Pequeno'),
    (50.00, 6, 1, 'true', 'false', 'false', 'Acai Grande'),
    (50.00, 6, 1, 'true', 'false', 'false', 'Acai Com Ninho'),
    (50.00, 6, 1, 'true', 'false', 'false', 'Acai Com Nutela');


INSERT INTO USUARIO
        (EMAIL_USUARIO, SENHA_USUARIO)
VALUES ('josue@gmail.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC');


--
--    INSERT INTO PERDA
--            (FK_ID_PROD_PERD, DT_PERDA_PERD, QTD_PRODUTO_PERD)
--    VALUES  (2, '2024-10-05', 2),
--            (5, '2024-09-15', 1),
--            (6, '2024-05-13', 5),
--            (9, '2024-07-26', 4),
--            (30, '2024-06-30', 2);
--    /*
--        Comentar de volta quando terminar
--    */
--    INSERT INTO VENDAS
--            (DATA_COMPRA_VEND)
--    VALUES  ('2024-10-13T15:45:20'),
--            ('2024-12-06T15:45:20'),
--            ('2024-12-06T15:50:20'),
--            ('2025-02-08'),
--            ('2025-02-09'),
--            ('2025-02-10'),
--            ('2025-02-02'),
--            ('2025-02-01');
--
--    INSERT INTO VENDAS_PRODUTO
--            (FK_ID_VEND_VENP, FK_ID_PROD_VENP, QTD_PRODUTOS_VENDIDO)
--    VALUES  (1, 2, 30),
--            (1, 3, 20),
--            (2, 6, 40),
--            (2, 20, 10),
--            (3, 9, 34),
--            (6, 62, 10),
--            (5, 78, 5),
--            (5, 90, 10),
--            (7, 3, 20);

INSERT INTO NOTIFICACAO
       (EMAIL_NOTI, FK_ID_PROD_NOTI)
VALUES ('bruno@gmail.com', 20),
       ('guilherme@gmail.com', 35),
       ('gus@gmail.com', 15),
       ('kauan@gmail.com', 11),
       ('thamires@gmail.com', 19),
       ('maria@gmail.com', 56);

--INSERT INTO LOTE
--    (DATA_PEDIDO_LOTE, DATA_ENTREGA_LOTE, DATA_VENCIMENTO_LOTE, QTD_PRODUTO_COMPRADO_LOTE, VALOR_LOTE, FK_ID_PROD_LOTE)
--VALUES  ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 1),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 2),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 3),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 4),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 5),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 6),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 7),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 8),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 9),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 10),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 12),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 13),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 14),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 16),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 17),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 18),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 20),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 21),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 22),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 24),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 25),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 26),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 28),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 29),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 30),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 32),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 33),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 34),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 36),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 37),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 39),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 40),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 81),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 93),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 66),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 46),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 47),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 48),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 49),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 42),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 92),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 80),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 63),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 62),
--        ('2024-10-02', '2024-10-05', '2025-05-20', 50, 150.00, 110);

INSERT INTO DESTAQUE
       (ID_DEST, FK_ID_PROD_DEST, DATA_DEST, TEXTO_DEST)
VALUES (1, 1, '2025-02-24', 'O sorvete de Nescolak é uma delícia cremosa que combina o sabor intenso do achocolatado com a textura suave do sorvete. Ele possui uma base de creme gelado, delicadamente misturado com o inconfundível sabor do Nesquik');

INSERT INTO RECOMENDACAO (ID_RECO, FK_ID_PROD_RECO) VALUES (1, 1);
