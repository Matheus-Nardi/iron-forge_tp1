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
    ('joao.silva', 'OaF4paP+SqLenJW8Rjrk5goXvajn1knmWXfupry55Kbkpll5xliVH9X3U8hMf6l4x/d2WnYf30xNVYTtLu7jmQ==', 2),  -- Senha é abacatecompimenta
    ('wheyHot', '123456', 2),
    ('maxuel', 'qOf0Qw7LIP5PqpJJMwKqcX6BZ+t+elaNZW+fBPY70EsAhd2deno3eb2KnjlTMC06GwBMhShPtQJM8k308HXQog==' , 1), -- Senha é flamengo
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

-- Inserir dados em fullnutrient
INSERT INTO fullnutrient (attr_id, value) VALUES
(0, 500), -- Exemplo: Vitamina A, 500 IU
(1, 200), -- Exemplo: Cálcio, 200 mg
(2, 100); -- Exemplo: Potássio, 100 mg

-- Inserir dados em photo
INSERT INTO photo (thumb, highres, is_user_uploaded) VALUES
('http://example.com/thumb1.jpg', 'http://example.com/highres1.jpg', true),
('http://example.com/thumb2.jpg', 'http://example.com/highres2.jpg', false),
('http://example.com/thumb3.jpg', 'http://example.com/highres3.jpg', true),
('http://example.com/thumb4.jpg', 'http://example.com/highres4.jpg', false),
('http://example.com/thumb5.jpg', 'http://example.com/highres5.jpg', true);



-- Inserir dados em food
INSERT INTO food (
    food_name, brand_name, serving_qty, serving_unit, serving_weight_grams,
    nf_metric_qty, nf_metric_uom, nf_calories, nf_total_fat, nf_saturated_fat,
    nf_cholesterol, nf_sodium, nf_total_carbohydrate, nf_dietary_fiber, nf_sugars,
    nf_protein, nf_potassium, nf_p, nix_brand_name, nix_brand_id, nix_item_name,
    nix_item_id, source, ndb_no, tags, alt_measures, lat, lng, note, class_code,
    brick_code, tag_id, updated_at, nf_ingredient_statement, id_photo
)
VALUES 
('Whey Protein 1', 'BrandX', 1, 'scoop', 30, 24, 'g', 120, 1, '0g', '0mg', 50, 3, '1g', '2g', 24, '500mg', 'Protein supplement', 'BrandX', '12345', 'Whey Protein Item 1', '67890', 1, '123456789', 'protein, supplement', 'scoop', '40.7128', '-74.0060', 'Good for muscle growth', 'Whey', 'Protein', 'TAG123', CURRENT_TIMESTAMP, 'Whey protein isolate', 1),
  
('Whey Protein 2', 'BrandY', 1, 'scoop', 30, 24, 'g', 130, 2, '1g', '1mg', 60, 4, '1g', '3g', 25, '600mg', 'Protein supplement', 'BrandY', '22345', 'Whey Protein Item 2', '78890', 1, '223456789', 'protein, supplement', 'scoop', '41.7128', '-75.0060', 'Supports muscle recovery', 'Whey', 'Protein', 'TAG124', CURRENT_TIMESTAMP, 'Whey protein concentrate',2),

('Whey Protein 3', 'BrandZ', 1, 'scoop', 35, 30, 'g', 140, 3, '0g', '2mg', 70, 5, '2g', '4g', 26, '700mg', 'Protein supplement', 'BrandZ', '32345', 'Whey Protein Item 3', '89890', 1, '323456789', 'protein, supplement', 'scoop', '42.7128', '-76.0060', 'Ideal for post-workout', 'Whey', 'Protein', 'TAG125', CURRENT_TIMESTAMP, 'Whey protein isolate',3),

('Whey Protein 4', 'BrandA', 1, 'scoop', 28, 22, 'g', 110, 2, '1g', '0mg', 40, 2, '1g', '3g', 22, '400mg', 'Protein supplement', 'BrandA', '42345', 'Whey Protein Item 4', '98890', 1, '423456789', 'protein, supplement', 'scoop', '43.7128', '-77.0060', 'Helps build muscle mass', 'Whey', 'Protein', 'TAG126', CURRENT_TIMESTAMP, 'Whey protein concentrate',
 4),

('Whey Protein 5', 'BrandB', 1, 'scoop', 32, 25, 'g', 150, 4, '0g', '1mg', 80, 6, '3g', '5g', 28, '800mg', 'Protein supplement', 'BrandB', '52345', 'Whey Protein Item 5', '108890', 1, '523456789', 'protein, supplement', 'scoop', '44.7128', '-78.0060', 'For muscle endurance', 'Whey', 'Protein', 'TAG127', CURRENT_TIMESTAMP, 'Whey protein isolate',5);


-- Inserção de whey protein
-- Inserir dados na tabela wheyprotein com todos os campos
INSERT INTO wheyprotein (
    upc, nome, descricao, preco, peso, id_sabor, tipoWhey, id_fabricante, id_food
) 
VALUES
    ('815044020354','Ultra Whey Supreme', 'Proteína de alta qualidade com sabor de chocolate.', 149.90, 900, 1, 1, 1, 1),
    ('631656711325','Delícia de Chocolate Whey', 'Sabor incrível de chocolate, ideal para smoothies.', 139.90, 900, 2, 1, 2, 2),
    ('883309010105','Baunilha Mágica', 'Whey de baunilha com ingredientes que aumentam o foco.', 154.90, 1000, 3, 2, 1, 3),
    ('631656714142','Cookies Whey Delight', 'Combinação perfeita de cookies e proteína.', 159.90, 800, 5, 2, 2, 4);

-- Inserção de lotes
INSERT INTO lote (quantidade, dataFabricacao, id_whey, codigo) VALUES
    (100, '2024-01-10', 1, 'COD-WHEY-001'),
    (150, '2024-01-12', 1, 'COD-WHEY-002'),
    (200, '2024-01-15', 2, 'COD-WHEY-003'),
    (180, '2024-01-20', 2, 'COD-WHEY-004'),
    (120, '2024-01-18', 3, 'COD-WHEY-005'),
    (90, '2024-01-22', 4, 'COD-WHEY-006');
-- Inserção de cupons
INSERT INTO cupom (id_fabricante, codigo, percentualdesconto, datavalidade, ativo) VALUES
    (1, 'WHEY10', 0.1, '2024-12-31T23:59:59', true), 
    (1, 'WHEY20', 0.2, '2024-11-30T23:59:59', false), 
    (1, 'FRETEWHEY', 0.0, '2024-10-31T23:59:59', true), 
    (2, 'WHEY30', 0.3, '2024-12-15T23:59:59', false), 
    (2, 'WHEYBLACK', 0.25, '2024-09-01T23:59:59', true),
    (2, 'SUPER20', 0.20, '2024-12-01T23:59:59', true), 
    (2, 'GROWTH20', 0.20, '2024-02-20T23:59:59', true);
