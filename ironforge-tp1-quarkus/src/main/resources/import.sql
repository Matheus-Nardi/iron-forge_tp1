-- Inserção de estados
INSERT INTO estado (nome, sigla)
VALUES ('Tocantins', 'TO'),
       ('Sao Paulo', 'SP'),
       ('Rio de Janeiro', 'RJ');

-- Inserção de cidades
INSERT INTO cidade (nome, id_estado)
VALUES ('Palmas', 1),
       ('Campinas', 2);

-- Inserção de fabricantes
INSERT INTO fabricante (nome, cnpj, email)
VALUES ('Max Titanium', '07578713000429', 'maxtitanium@maxtitanium.com.br'),
       ('Growth', '0832644000108', 'sac@growthsupplements.com.br');

-- Inserção de sabores
INSERT INTO sabor (nome)
VALUES ('Morango'),
       ('Chocolate'),
       ('Baunilha'),
       ('Neutro'),
       ('Cookies and Cream');

-- Inserção de wheyprotein
INSERT INTO wheyprotein (nome, descricao, preco, peso, id_sabor, id_fabricante)
VALUES ('Whey da Growth', 'whey de 1kg', 88.90, 1000, 1, 2),
       ('Whey da Growth', 'whey de 1kg', 110, 1000, 2, 2),
       ('Whey da Growth', 'whey de 1kg', 70, 1000, 4, 2),
       ('Whey da Max', 'whey de 900g', 100.90, 900, 3, 1),
       ('Whey da Max', 'whey de 900g', 130.90, 900, 5, 1);
