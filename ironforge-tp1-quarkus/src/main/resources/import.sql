-- Inserção de estados
INSERT INTO estado (nome, sigla) VALUES 
    ('Tocantins', 'TO'),
    ('Sao Paulo', 'SP'),
    ('Rio de Janeiro', 'RJ');

-- Inserção de cidades
INSERT INTO cidade (nome, id_estado) VALUES 
    ('Palmas', 1),
    ('Rio de Janeiro', 3),
    ('Gurupi', 1),
    ('São Paulo', 2);

-- Inserção de usuários
INSERT INTO usuario (username, senha, perfil) VALUES 
    ('joao.silva', 'acabatecompimenta', 2),
    ('wheyHot', '123456', 2),
    ('maxuel', 'flamengo' , 1),
    ('growthsuplementos', 'senhaSegura',2);

-- Inserção de pessoas
INSERT INTO pessoa (id_usuario, email, nome) VALUES 
    (1, 'joaosilva@hotmail.com', 'João Silva'),
    (2, 'wheyHot@gmail.com', 'Whey Hot'),
    (3, 'maxuel@gmail.com', 'Maxuel Filho Pinto'),
    (4, 'growthsuplementos@gmail.com', 'Growth Suplementos');

-- Inserção de pessoas físicas (referenciando IDs existentes na tabela pessoa)
INSERT INTO pessoafisica (id, datanascimento, cpf) VALUES 
    (1, '2001-10-05', '72446262074'),  -- João Silva
    (3, '1995-07-23', '81384998004');  -- Maxuel Filho Pinto

-- Inserção de pessoa jurídica (referenciando ID existente na tabela pessoa)
INSERT INTO pessoajuridica (id, cnpj) VALUES 
    (2, '52195050000175'),  -- Whey Hot
    (4, '73395303000124');  -- Growth Suplementos

-- Inserção de cliente (relacionado com pessoa física)
INSERT INTO cliente (id_pessoafisica) VALUES 
    (1);  -- Cliente: João Silva

-- Inserção de funcionário (relacionado com pessoa física)
INSERT INTO funcionario (id_pessoafisica, cargo, salario, datacontratacao) VALUES 
    (3, 'Analista de Sistemas', 15000, '2023-02-09');  -- Funcionário: Maxuel

-- Inserção de fabricante (relacionado com pessoa jurídica)
INSERT INTO fabricante (id_pessoajuridica) VALUES 
    (2),  -- Whey Hot
    (4);  -- Growth Suplementos

-- Inserção de telefones
INSERT INTO telefone (codigoArea, numero, id_pessoa) VALUES 
    ('45', '912344789', 1),  -- João Silva
    ('85', '958767010', 2),  -- Whey Hot
    ('31', '905071884', 3),  -- Maxuel
    ('63', '977771234', 4);  -- Growth Suplementos

-- Inserção de endereços
INSERT INTO endereco (
    id_cidade, logradouro, bairro, numero, complemento, cep, id_pessoa
) VALUES 
    (1, 'Rua das Flores', 'Jardim Primavera', '123', 'Apto 101', '12345-678', 1),  -- João Silva
    (2, 'Avenida Central', 'Centro', '456', NULL, '23456-789', 2),  -- Whey Hot
    (3, 'Travessa da Paz', 'Vila Nova', '789', 'Bloco B', '34567-890', 3),  -- Maxuel
    (4, 'Rua da Harmonia', 'Boa Vista', '101', 'Casa 2', '56789-101', 4);  -- Growth Suplementos

-- Inserção de sabores
INSERT INTO sabor (nome) VALUES 
    ('Morango'),
    ('Chocolate'),
    ('Baunilha'),
    ('Neutro'),
    ('Cookies and Cream');

-- Inserção de whey protein
INSERT INTO wheyprotein (
    upc,
    nome,
    descricao,
    preco,
    peso,
    id_sabor,
    tipoWhey,
    id_fabricante
) VALUES 
    ('815044020354','Ultra Whey Supreme', 'Proteína de alta qualidade com sabor de chocolate.', 149.90, 900, 1, 1, 1),
    ('631656711325','Delícia de Chocolate Whey', 'Sabor incrível de chocolate, ideal para smoothies.', 139.90, 900, 2, 1, 2),
    ('883309010105','Baunilha Mágica', 'Whey de baunilha com ingredientes que aumentam o foco.', 154.90, 1000, 3, 2, 1),
    ('631656714142','Cookies Whey Delight', 'Combinação perfeita de cookies e proteína.', 159.90, 800, 5, 2, 2),
    ('631656716627','Neutro Energizante', 'Sabor neutro para misturar com qualquer receita.', 129.90, 900, 4, 3, 1),
    (' 631656714142','Trem de Chocolate', 'Experimente a explosão de chocolate na sua proteína.', 169.90, 950, 1, 3, 2);

-- Inserção de lotes
INSERT INTO lote (quantidade, dataFabricacao, id_whey, codigo) VALUES
    (100, '2024-01-10', 1, 'COD-WHEY-001'),
    (150, '2024-01-12', 1, 'COD-WHEY-002'),
    (200, '2024-01-15', 2, 'COD-WHEY-003'),
    (180, '2024-01-20', 2, 'COD-WHEY-004'),
    (120, '2024-01-18', 3, 'COD-WHEY-005'),
    (90, '2024-01-22', 4, 'COD-WHEY-006'),
    (160, '2024-01-25', 5, 'COD-WHEY-007'),
    (140, '2024-01-28', 6, 'COD-WHEY-008');

-- Inserção de cupons
INSERT INTO cupom (id_fabricante, codigo, percentualdesconto, datavalidade, ativo) VALUES
    (1, 'WHEY10', 10.0, '2024-12-31T23:59:59', true), 
    (1, 'WHEY20', 20.0, '2024-11-30T23:59:59', false), 
    (1, 'FRETEWHEY', 0.0, '2024-10-31T23:59:59', true), 
    (2, 'WHEY30', 30.0, '2024-12-15T23:59:59', false), 
    (2, 'WHEYBLACK', 25.0, '2024-09-01T23:59:59', true),
    (2, 'SUPER20', 5.0, '2024-12-01T23:59:59', true), 
    (2, 'GROWTH20', 50.0, '2024-02-20T23:59:59', true);
