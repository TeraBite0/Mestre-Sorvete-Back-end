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
       (NOME_SUBT, FK_ID_TIPO_SUBT)
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
        (PRECO_PROD, FK_ID_SUBT_PROD, FK_ID_MARCA_PROD, QTD_CAIXA_ESTOQUE_PROD, QTD_POR_CAIXA_PROD, IS_ATIVO_PROD, DISPONIVEL_PROD, TEM_LACTOSE_PROD, TEM_GLUTEN_PROD, NOME_PROD)
    VALUES
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Nescolak'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Cookies'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Biscoitos'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Brigadeirissimo'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Maximum: Paçoca Cremosa'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Premium: Chocolate Trufado'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Mousse De Maracuja'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Black Classico'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Premium: Frutas Vermelhas'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Petit Gateau'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Gourmet: Cocada Baiana'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Trufado Chocolate'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Torta De Limao'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Trufa'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Caramelo'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Bolo De Brownie'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Bolo De Cenoura'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Torta Holandesa'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Torta De Limao'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Avela'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Bem Casado'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Petit Gateau'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Duo Amore'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Brounie Com Doce De Leite'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Morango'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Doce De Leite'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Chocolate'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Leite Condensado'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Abacaxi Suico'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Mousse De Uva'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Mamao Com Casca'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Limao Suico'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Coco Branco'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Coco Queimado'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Chiclete'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Banana'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Milho Verde'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Leite Ninho'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Uva'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Leite Condensado'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Limao Suico'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Caramelo'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Leite Ninho Trufado'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Brigadeiro'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Chocolate Black'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Franuito'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Tentacao'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Chocoblitz'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'ChocoTella'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Queijo'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Pistache'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Acai'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Divino'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Skimo'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Delirio'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Brigadeiro Com Morango'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Chocolate Trufado'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Pistache Classic'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Banoffe Pie'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Torta De Limao'),
    (07.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Picole Zero Leite'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Nino Trufado'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Pistache Com ChocolateBranco'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Morango Com LeiteCondensado'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Duete'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Doce De Leite Com Cobertura De Chocolate'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Show Tella'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Krocantissimo'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Creme Club Hula Hula Coco'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Grego'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Creme e Maracuja'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Acai Com Guarana'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Mashmallow'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Graviola'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Fruta fa Cupuaçu'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Banana Caramelada'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Palha Cinho'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Amendoim'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Abacate'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Romeu e Julieta'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Churros'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Mousse De Maracuja'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Flocos'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Iogurte Com Frutas Vermelhas'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Ameixa'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Acai'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Abacaxi Hortela'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Mini Saia'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Melancia'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Manga'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Jabuticaba'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Tamarindo'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Maracuja'),
    (10.00, 1, 1, 0, 30, 'true', 'true', 'false', 'false', 'Goiaba'),
    (10.00, 4, 1, 0, 30, 'true', 'true', 'false', 'false', 'Banoffe'),
    (10.00, 4, 1, 0, 30, 'true', 'true', 'false', 'false', 'Chocolate Baunilha'),
    (10.00, 4, 1, 0, 30, 'true', 'true', 'false', 'false', 'Trufado'),
    (10.00, 4, 1, 0, 30, 'true', 'true', 'false', 'false', 'Crocante'),
    (50.00, 2, 1, 0, 30, 'true', 'true', 'false', 'false', 'Nino Com Creme De Avela'),
    (50.00, 2, 1, 0, 30, 'true', 'true', 'false', 'false', 'Nino Com Morango'),
    (50.00, 2, 1, 0, 30, 'true', 'true', 'false', 'false', 'Nino Trufado'),
    (50.00, 2, 1, 0, 30, 'true', 'true', 'false', 'false', 'Ninho Com Doce De Leite'),
    (50.00, 2, 1, 0, 30, 'true', 'true', 'false', 'false', 'Rafaelo'),
    (50.00, 2, 1, 0, 30, 'true', 'true', 'false', 'false', 'Kinder'),
    (50.00, 2, 1, 0, 30, 'true', 'true', 'false', 'false', 'Pave Italiano'),
    (50.00, 2, 1, 0, 30, 'true', 'true', 'false', 'false', 'Doce De Leite Folhado'),
    (50.00, 2, 1, 0, 30, 'true', 'true', 'false', 'false', 'Iogurte Grego Com Morango'),
    (50.00, 3, 1, 0, 30, 'true', 'true', 'false', 'false', 'Napolitano'),
    (50.00, 7, 1, 0, 30, 'true', 'true', 'false', 'false', 'Acai Pequeno'),
    (50.00, 6, 1, 0, 30, 'true', 'true', 'false', 'false', 'Acai Grande'),
    (50.00, 6, 1, 0, 30, 'true', 'true', 'false', 'false', 'Acai Com Ninho'),
    (50.00, 6, 1, 0, 30, 'true', 'true', 'false', 'false', 'Acai Com Nutela');


INSERT INTO USUARIO
        (EMAIL_USUARIO, SENHA_USUARIO)
VALUES ('josue@gmail.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC');


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

INSERT INTO DESTAQUE
       (ID_DEST, FK_ID_PROD_DEST, DATA_DEST, TEXTO_DEST)
VALUES (1, 1, '2025-02-24', 'O sorvete de Nescolak é uma delícia cremosa que combina o sabor intenso do achocolatado com a textura suave do sorvete. Ele possui uma base de creme gelado, delicadamente misturado com o inconfundível sabor do Nesquik');

INSERT INTO RECOMENDACAO
        (ID_RECO, FK_ID_PROD_RECO)
VALUES  (1, 1),
        (2, 2),
        (3, 3),
        (4, 4),
        (5, 5),
        (6, 6),
        (7, 7),
        (8, 8),
        (9, 9),
        (10, 10),
        (11, 11),
        (12, 12);

INSERT INTO FORNECEDOR
        (NOME_FORN)
VALUES ('Fornecedor Senhor Sorvete'),
       ('Fornecedor PimPinella'),
       ('Fornecedor Gelone'),
       ('Fornecedor Mr Mix');

INSERT INTO LOTE_STATUS
        (NOME_STATUS)
VALUES ('Aguardando entrega'),
       ('Entregue'),
       ('Cancelado'),
       ('Entrege com pendência'),
       ('Concluído com pendência');