-- Inserção de estados
INSERT INTO
    estado (nome, sigla)
VALUES
    ('Tocantins', 'TO'),
    ('Sao Paulo', 'SP'),
    ('Rio de Janeiro', 'RJ');

-- Inserção de cidades
INSERT INTO
    cidade (nome, id_estado)
VALUES
    ('Palmas', 1),
    ('Campinas', 2);

INSERT INTO telefone(codigoArea,numero)
values('45' , '912344789'), ('85', '958767010') , ('31' , '905071884') , ('63' , '977771234');


INSERT INTO usuario(nome,cpf,email,senha,dataNascimento, id_telefone)
values('Pedro', '86646618046' , 'pedro@gmail.com', '123', '1999-10-10' , 1) ,
('Matheus', '49951236014', 'matheu@gmail.com', 'arroz' , '1999-04-06', 2);

INSERT INTO cliente(id_usuario)
values(1);

INSERT INTO funcionario(id_usuario, salario)
values (2, 10000);



-- Inserção de fabricantes
INSERT INTO
    fabricante (nome, cnpj, email, id_telefone)
VALUES
    (
        'Max Titanium',
        '07578713000429',
        'maxtitanium@maxtitanium.com.br',
        3
    ),
    (
        'Growth',
        '0832644000108',
        'sac@growthsupplements.com.br',
        4
    );

-- Inserção de sabores
INSERT INTO
    sabor (nome)
VALUES
    ('Morango'),
    ('Chocolate'),
    ('Baunilha'),
    ('Neutro'),
    ('Cookies and Cream');

-- Inserção de wheyprotein
INSERT INTO
    wheyprotein (
        nome,
        descricao,
        preco,
        peso,
        id_sabor,
        id_fabricante
    )
VALUES
    (
        'Whey da Growth',
        'whey de 1kg',
        88.90,
        1000,
        1,
        2
    ),
    ('Whey da Growth', 'whey de 1kg', 110, 1000, 2, 2),
    ('Whey da Growth', 'whey de 1kg', 70, 1000, 4, 2),
    ('Whey da Max', 'whey de 900g', 100.90, 900, 3, 1),
    ('Whey da Max', 'whey de 900g', 130.90, 900, 5, 1);