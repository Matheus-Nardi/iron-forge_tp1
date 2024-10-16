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
    ('Campinas', 2),
    ('Gurupi', 1),
    ('São Paulo', 2);

INSERT INTO telefone(codigoArea,numero)
values('45' , '912344789'), ('85', '958767010') , ('31' , '905071884') , ('63' , '977771234');

INSERT INTO endereco (id_cidade, logradouro, bairro, numero, complemento, cep) 
VALUES 
(1, 'Rua das Flores', 'Jardim Primavera', '123', 'Apto 101', '12345-678'),
(2, 'Avenida Central', 'Centro', '456', NULL, '23456-789'),
(3, 'Travessa da Paz', 'Vila Nova', '789', 'Bloco B', '34567-890'),
(4, 'Rua da Harmonia', 'Boa Vista', '101', 'Casa 2', '56789-101'),
(4, 'Avenida das Palmeiras', 'Jardim Alegre', '567', 'Cobertura', '67890-123');;


INSERT INTO usuario(nome,cpf,email,senha,dataNascimento)
values('Pedro', '86646618046' , 'pedro@gmail.com', '123', '1999-10-10') ,
('Matheus', '49951236014', 'matheu@gmail.com', 'arroz' , '1999-04-06');

INSERT INTO usuario_telefone(id_usuario, id_telefone)
values(1,1), (2,2);

INSERT INTO usuario_endereco(id_usuario, id_endereco)
values(1,1), (1,3) , (2,2);


INSERT INTO cliente(id_usuario)
values(1);

INSERT INTO funcionario(id_usuario, salario)
values (2, 10000);



-- Inserção de fabricantes
INSERT INTO
    fabricante (nome, cnpj, email)
VALUES
    (
        'Max Titanium',
        '07578713000429',
        'maxtitanium@maxtitanium.com.br'
    ),
    (
        'Growth',
        '0832644000108',
        'sac@growthsupplements.com.br'
    );

INSERT INTO fabricante_telefone(id_fabricante, id_telefone)
values(1,3),(2,4);

INSERT INTO fabricante_endereco(id_fabricante, id_endereco)
values (1,4),(2,5);


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
        tipoWhey,
        id_fabricante
    )
VALUES
    (
        'Whey da Growth',
        'whey de 1kg',
        88.90,
        1000,
        1,
        1,
        2
    ),
    ('Whey da Growth', 'whey de 1kg', 110, 1000, 2,1,2),
    ('Whey da Growth', 'whey de 1kg', 70, 1000, 4, 2,2),
    ('Whey da Max', 'whey de 900g', 100.90, 900, 3, 3,1),
    ('Whey da Max', 'whey de 900g', 130.90, 900, 5, 1,1);

INSERT INTO lote (quantidade, dataFabricacao, id_whey, codigo)
VALUES
(50, CURRENT_DATE, 1, CONCAT('L-', CURRENT_DATE::TEXT, '-WHEY')),
(100, CURRENT_DATE - INTERVAL '1 week', 2, CONCAT('L-', (CURRENT_DATE - INTERVAL '1 week')::TEXT, '-WHEY')),
(60, CURRENT_DATE - INTERVAL '2 weeks', 3, CONCAT('L-', (CURRENT_DATE - INTERVAL '2 weeks')::TEXT, '-WHEY'));

INSERT INTO cupom ( id_fabricante, codigo, percentualdesconto, datavalidade, ativo) VALUES
( 1, 'WHEY10', 10.0, '2024-12-31T23:59:59', true), 
( 2, 'WHEY20', 20.0, '2024-11-30T23:59:59', false), 
( 1, 'FRETEWHEY', 0.0, '2024-10-31T23:59:59', true), 
( 2, 'WHEY30', 30.0, '2024-12-15T23:59:59', false), 
( 1, 'WHEYBLACK', 25.0, '2024-09-01T23:59:59', true),
( 1, 'MAXW20', 5.0, '2024-12-01T23:59:59', true), 
( 2, 'GROWTH20', 50.0, '2024-02-20T23:59:59', true); 