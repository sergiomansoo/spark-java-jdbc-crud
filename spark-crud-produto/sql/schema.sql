-- 1. Criação do Banco de Dados (ajuda a testar)
-- CREATE DATABASE exercicio_spark;

-- 2. Criação da Tabela de Produtos
CREATE TABLE IF NOT EXISTS produto (
    id SERIAL PRIMARY KEY,              -- SERIAL gera o ID automático (Auto-incremento)
    nome VARCHAR(100) NOT NULL,         -- Nome obrigatório
    descricao TEXT,                     -- Descrição opcional (pode ser longa)
    preco DECIMAL(10, 2) NOT NULL       -- Preço com 2 casas decimais
);

-- 3. Inserção de dados iniciais  (para testar)
INSERT INTO produto (nome, descricao, preco) VALUES 
('Notebook', 'Intel i7, 16GB RAM', 4500.00),
('Mouse Gamer', 'RGB, 12000 DPI', 150.00);