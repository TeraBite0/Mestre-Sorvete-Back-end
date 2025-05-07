INSERT INTO TIPO
        (NOME_TIPO)
VALUES ('Picolé'),
       ('Saquinho'),
       ('Copo 200ml'),
       ('Copo 240ml'),
       ('Cone'),
       ('Pote 1.8L');

INSERT INTO SUBTIPO
       (NOME_SUBT, FK_ID_TIPO_SUBT)
VALUES ('Base Água Tradicional', 1),
       ('Ao Leite Tradicional', 1),
       ('Ao Leite Tradicional Copo 240ml', 1),
       ('Ao Leite Recheado', 1),
       ('Ao Leite Zero', 1),
       ('Ao Leite Especial', 1),
       ('Ao Leite Paleta', 1),
       ('Ao Leite Soft', 1),
       ('Ao Leite Clássico', 1),
       ('Ao Leite Premium', 1),
       ('Ao Leite Gourmet Pote 1.8L', 6),
       ('Ao Leite Sortidos', 2),
       ('Ao Leite Blend', 5),
       ('Ao Leite Trufado', 5),
       ('Ao Leite Gourmet', 1),
       ('Base Água Tradicional', 1),
       ('Base Água Tradicional Copo 200ml', 3),
       ('Base Água Especial', 1),
       ('Base Água Zero', 1),
       ('Base Água/Leite Especial', 1),
       ('Base Água/Leite Paleta', 1),
       ('Base Água Gourmet', 1);

INSERT INTO MARCA
        (NOME_MARCA)
VALUES ('Sr. Sorvete'),
       ('Geloni'),
       ('Kascão'),
       ('Pimpinella'),
       ('Eskimó'),
       ('Mr-Mix'),
       ('Sorvete da Chácara'),
       ('Cream Color'),
       ('Icream');

INSERT INTO PRODUTO
        (PRECO_PROD, FK_ID_SUBT_PROD, FK_ID_MARCA_PROD, QTD_CAIXA_ESTOQUE_PROD, QTD_POR_CAIXA_PROD, IS_ATIVO_PROD, DISPONIVEL_PROD, TEM_LACTOSE_PROD, TEM_GLUTEN_PROD, NOME_PROD)
    VALUES
    (2.00, 12, 2, 0, 50, 'true', 'true', 'false', 'false', 'Iogurte'),
    (2.00, 16, 1, 0, 50, 'true', 'true', 'false', 'false', 'Pinta Lingua'),
    (2.00, 16, 1, 0, 50, 'true', 'true', 'false', 'false', 'Abacaxi'),
    (2.00, 16, 1, 0, 50, 'true', 'true', 'false', 'false', 'Maracujá'),
    (2.00, 16, 1, 0, 50, 'true', 'true', 'false', 'false', 'Tangerina'),
    (2.00, 16, 1, 0, 50, 'true', 'true', 'false', 'false', 'Melancia'),
    (2.00, 16, 1, 0, 50, 'true', 'true', 'false', 'false', 'Uva'),
    (2.00, 16, 1, 0, 50, 'true', 'true', 'false', 'false', 'Manga'),
    (2.00, 16, 1, 0, 50, 'true', 'true', 'false', 'false', 'Limão'),
    (2.00, 16, 1, 0, 50, 'true', 'true', 'false', 'false', 'Groselha'),
    (3.00, 16, 1, 0, 50, 'true', 'true', 'false', 'false', 'Açai'),
    (3.00, 16, 1, 0, 50, 'true', 'true', 'false', 'false', 'Sr. Verão'),
    (3.00, 16, 3, 0, 28, 'true', 'true', 'false', 'false', 'Abacaxi com hortelã'),
    (3.00, 2, 1, 0, 50, 'true', 'true', 'false', 'false', 'Milho'),
    (3.00, 2, 1, 0, 50, 'true', 'true', 'false', 'false', 'Leite Condensado'),
    (3.00, 2, 1, 0, 50, 'true', 'true', 'false', 'false', 'Amendoim'),
    (3.00, 2, 1, 0, 50, 'true', 'true', 'false', 'false', 'Coco Branco'),
    (3.00, 2, 1, 0, 50, 'true', 'true', 'false', 'false', 'Coco Queimado'),
    (3.00, 2, 1, 0, 50, 'true', 'true', 'false', 'false', 'Leite Ninho'),
    (3.00, 2, 1, 0, 50, 'true', 'true', 'false', 'false', 'Banana'),
    (3.00, 4, 1, 0, 50, 'true', 'true', 'false', 'false', 'Limão Suiço'),
    (3.00, 4, 1, 0, 50, 'true', 'true', 'false', 'false', 'Abacaxi Suiço'),
    (3.00, 4, 1, 0, 50, 'true', 'true', 'false', 'false', 'Mousse de Uva'),
    (3.00, 4, 1, 0, 50, 'true', 'true', 'false', 'false', 'Morango'),
    (3.00, 4, 1, 0, 50, 'true', 'true', 'false', 'false', 'Doce de Leite'),
    (3.00, 4, 1, 0, 50, 'true', 'true', 'false', 'false', 'Mamão Papaia com Cassis'),
    (3.00, 4, 1, 0, 50, 'true', 'true', 'false', 'false', 'Chocolate'),
    (3.00, 4, 1, 0, 50, 'true', 'true', 'false', 'false', 'Chocomenta'),
    (3.00, 2, 4, 0, 26, 'true', 'true', 'false', 'false', 'Abacate'),
    (3.00, 2, 4, 0, 24, 'true', 'true', 'false', 'false', 'Palhacinho'),
    (3.00, 2, 4, 0, 24, 'true', 'true', 'false', 'false', 'Flocos'),
    (3.00, 4, 4, 0, 24, 'true', 'true', 'false', 'false', 'Tentação'),
    (3.00, 4, 4, 0, 24, 'true', 'true', 'false', 'false', 'Blueice com Chiclete'),
    (3.00, 4, 4, 0, 24, 'true', 'true', 'false', 'false', 'Romeu e Julieta'),
    (3.00, 4, 4, 0, 24, 'true', 'true', 'false', 'false', 'Iogurte com Frutas Vermelhas'),
    (6.00, 5, 5, 0, 45, 'true', 'true', 'false', 'false', 'Coco'),
    (6.00, 5, 2, 0, 20, 'true', 'true', 'false', 'false', 'Chocolate'),
    (6.00, 5, 6, 0, 28, 'true', 'true', 'false', 'false', 'Chocolate'),
    (6.00, 19, 6, 0, 28, 'true', 'true', 'false', 'false', 'Frutas Vermelhas'),
    (6.00, 6, 7, 0, 50, 'true', 'true', 'false', 'false', 'Queijo'),
    (6.00, 6, 6, 0, 28, 'true', 'true', 'false', 'false', 'Pistache'),
    (6.00, 6, 8, 0, 26, 'true', 'true', 'false', 'false', 'Tablets'),
    (6.00, 20, 2, 0, 28, 'true', 'true', 'false', 'false', 'Açai com Leite Condensado'),
    (6.00, 6, 1, 0, 40, 'true', 'true', 'false', 'false', 'Brigadeiro'),
    (6.00, 6, 1, 0, 40, 'true', 'true', 'false', 'false', 'Skimo'),
    (6.00, 6, 9, 0, 24, 'true', 'true', 'false', 'false', 'Chocotella'),
    (6.00, 6, 9, 0, 24, 'true', 'true', 'false', 'false', 'Fram Boito'), -- olhar depois
    (6.00, 6, 9, 0, 24, 'true', 'true', 'false', 'false', 'Tiplo Chocolate'),
    (6.00, 6, 9, 0, 24, 'true', 'true', 'false', 'false', 'Bombom de Valsa'),
    (6.00, 6, 9, 0, 24, 'true', 'true', 'false', 'false', 'Grego com Framboesa'),
    (6.00, 18, 9, 0, 24, 'true', 'true', 'false', 'false', 'Trio Tropical'),
    (6.00, 6, 5, 0, 38, 'true', 'true', 'false', 'false', 'Sagú'),
    (6.00, 6, 5, 0, 20, 'true', 'true', 'false', 'false', 'Bob Esponja'),
    (15.00, 17, 4, 0, 12, 'true', 'true', 'false', 'false', 'Açai com Granola'),
    (7.00, 3, 2, 0, 12, 'true', 'true', 'false', 'false', 'Napolitano'),
    (8.00, 13, 2, 0, 15, 'true', 'true', 'false', 'false', 'Cone'),
    (8.00, 14, 8, 0, 16, 'true', 'true', 'false', 'false', 'Conitto'),
    (10.00, 19, 1, 0, 40, 'true', 'true', 'false', 'false', 'Morango com Leite Condensado'),
    (10.00, 7, 1, 0, 40, 'true', 'true', 'false', 'false', 'Leite Ninho Trufado'),
    (10.00, 7, 6, 0, 20, 'true', 'true', 'false', 'false', 'Pistache com Chocolate Branco'),
    (10.00, 7, 6, 0, 20, 'true', 'true', 'false', 'false', 'Paçoca com Doce de Leite'),
    (5.00, 8, 8, 0, 20, 'true', 'true', 'false', 'false', 'Grego'),
    (5.00, 8, 2, 0, 20, 'true', 'true', 'false', 'false', 'Unicornio'),
    (5.00, 8, 2, 0, 16, 'true', 'true', 'false', 'false', 'Charminho'),
    (8.00, 9, 8, 0, 20, 'true', 'true', 'false', 'false', 'Biscoito'),
    (8.00, 9, 8, 0, 20, 'true', 'true', 'false', 'false', 'Nescolak'),
    (8.00, 9, 8, 0, 20, 'true', 'true', 'false', 'false', 'Cookies'),
    (8.00, 9, 8, 0, 20, 'true', 'true', 'false', 'false', 'Trufa'),
    (8.00, 9, 8, 0, 20, 'true', 'true', 'false', 'false', 'White'),
    (8.00, 9, 8, 0, 20, 'true', 'true', 'false', 'false', 'Black'),
    (8.00, 10, 8, 0, 20, 'true', 'true', 'false', 'false', 'Chocolate Trufado'),
    (8.00, 10, 8, 0, 20, 'true', 'true', 'false', 'false', 'Iogurte com Amarena'),
    (8.00, 10, 8, 0, 20, 'true', 'true', 'false', 'false', 'Frutas Vermelhas'),
    (8.00, 10, 8, 0, 20, 'true', 'true', 'false', 'false', 'Petit Gateau'),
    (8.00, 10, 8, 0, 20, 'true', 'true', 'false', 'false', 'Torta de Limão'),
    (8.00, 15, 8, 0, 18, 'true', 'true', 'false', 'false', 'Bolo de Cenoura'),
    (8.00, 15, 8, 0, 18, 'true', 'true', 'false', 'false', 'Bolo Brownie'),
    (8.00, 10, 9, 0, 20, 'true', 'true', 'false', 'false', 'Mousse Maracujá'),
    (8.00, 10, 9, 0, 20, 'true', 'true', 'false', 'false', 'Avelã'),
    (8.00, 10, 9, 0, 20, 'true', 'true', 'false', 'false', 'Crokito'),
    (8.00, 15, 9, 0, 20, 'true', 'true', 'false', 'false', 'Torta Holandesa'),
    (8.00, 15, 9, 0, 18, 'true', 'true', 'false', 'false', 'Bem Casado'),
    (8.00, 15, 9, 0, 18, 'true', 'true', 'false', 'false', 'Brownie com Doce de Leite'),
    (8.00, 15, 2, 0, 20, 'true', 'true', 'false', 'false', 'Red Valvet'),
    (8.00, 10, 2, 0, 20, 'true', 'true', 'false', 'false', 'Abacaxi ao vinho'),
    (8.00, 10, 2, 0, 20, 'true', 'true', 'false', 'false', 'Paçoca Cremosa'),
    (8.00, 10, 2, 0, 20, 'true', 'true', 'false', 'false', 'Salt Caramel'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Abacaxi Supremo'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Açaí'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Açaí com Avelã'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Açaí com Ninho'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Brownino'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Camafeu'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Coco com Abobora'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Doce de Leite Folhado'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Ferrero'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Iogurte com Amarena'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Iogurte Grego com Morango'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Kinder'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Lá Felicità'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Mascarpone com Goiabada'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Ninho com Avelã'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Ninho com Morango'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Ninho Trufado'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Panetone'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'PannaCotta com Frutas Vermelhas'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Pavê Ítaliano'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Pé de Moça'),
    (50.00, 11, 1, 0, 10, 'true', 'true', 'false', 'false', 'Rafaello'),
    (50.00, 22, 1, 0, 10, 'true', 'true', 'false', 'false', 'Sr. Verão');

INSERT INTO USUARIO
        (EMAIL_USUARIO, SENHA_USUARIO)
VALUES ('josue@gmail.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC');

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
