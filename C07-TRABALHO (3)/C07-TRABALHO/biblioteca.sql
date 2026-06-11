-- Segunda Entrega do Projeto - Banco de Dados Biblioteca
-- Alunos: Jose Matheus Goncalves Rodrigues, Petros Silveira Paradello
-- Tema: Biblioteca


DROP DATABASE IF EXISTS biblioteca;
CREATE DATABASE biblioteca;
USE biblioteca;



CREATE TABLE cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE carteirinha (
    id_carteirinha INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL UNIQUE,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    data_emissao DATE NOT NULL,
    validade DATE NOT NULL,
    CONSTRAINT fk_carteirinha_cliente
        FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente) on delete cascade,
    CONSTRAINT chk_validade_carteirinha
        CHECK (validade > data_emissao)
);

CREATE TABLE livro (
    id_livro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    sinopse TEXT,
    ano_publicacao INT NOT NULL,
    CONSTRAINT chk_ano_publicacao
        CHECK (ano_publicacao > 0)
);

CREATE TABLE autor (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nacionalidade VARCHAR(50) NOT NULL
);

CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(60) NOT NULL UNIQUE,
    descricao TEXT
);

CREATE TABLE edicao (
    id_edicao INT AUTO_INCREMENT PRIMARY KEY,
    id_livro INT NOT NULL,
    numero_edicao INT NOT NULL,
    editora VARCHAR(80) NOT NULL,
    preco_reposicao DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_edicao_livro
        FOREIGN KEY (id_livro) REFERENCES livro(id_livro) on delete cascade,
    CONSTRAINT chk_numero_edicao
        CHECK (numero_edicao > 0),
    CONSTRAINT chk_preco_reposicao
        CHECK (preco_reposicao > 0)
);

CREATE TABLE exemplar (
    id_exemplar INT AUTO_INCREMENT PRIMARY KEY,
    id_edicao INT NOT NULL,
    tombo VARCHAR(30) NOT NULL UNIQUE,
    status_exemplar VARCHAR(20) NOT NULL DEFAULT 'Disponivel',
    data_aquisicao DATE NOT NULL,
    CONSTRAINT fk_exemplar_edicao
        FOREIGN KEY (id_edicao) REFERENCES edicao(id_edicao) on delete cascade,
    CONSTRAINT chk_status_exemplar
        CHECK (status_exemplar IN ('Disponivel', 'Emprestado', 'Reservado', 'Manutencao'))
);

CREATE TABLE emprestimo (
    id_emprestimo INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    id_exemplar INT NOT NULL,
    data_emprestimo DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_prevista_devolucao DATE NOT NULL,
    data_devolucao DATE NULL,
    multa DECIMAL(8,2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_emprestimo_cliente
        FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    CONSTRAINT fk_emprestimo_exemplar
        FOREIGN KEY (id_exemplar) REFERENCES exemplar(id_exemplar) on delete cascade,
    CONSTRAINT chk_multa
        CHECK (multa >= 0)
);

CREATE TABLE livro_autor (
    id_livro INT NOT NULL,
    id_autor INT NOT NULL,
    PRIMARY KEY (id_livro, id_autor),
    CONSTRAINT fk_livro_autor_livro
        FOREIGN KEY (id_livro) REFERENCES livro(id_livro) on delete cascade,
    CONSTRAINT fk_livro_autor_autor
        FOREIGN KEY (id_autor) REFERENCES autor(id_autor) on delete cascade
);

CREATE TABLE livro_categoria (
    id_livro INT NOT NULL,
    id_categoria INT NOT NULL,
    PRIMARY KEY (id_livro, id_categoria),
    CONSTRAINT fk_livro_categoria_livro
        FOREIGN KEY (id_livro) REFERENCES livro(id_livro) on delete cascade,
    CONSTRAINT fk_livro_categoria_categoria
        FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria) on delete cascade
);



INSERT INTO cliente (nome, cpf, email, data_nascimento, ativo) VALUES
('Ana Souza', '11111111111', 'ana.souza@email.com', '2000-04-15', TRUE),
('Bruno Lima', '22222222222', 'bruno.lima@email.com', '1998-09-21', TRUE),
('Carla Mendes', '33333333333', 'carla.mendes@email.com', '2002-01-10', TRUE),
('Daniel Costa', '44444444444', 'daniel.costa@email.com', '1995-12-03', TRUE),
('Eduarda Alves', '55555555555', 'eduarda.alves@email.com', '2001-07-30', TRUE);

INSERT INTO carteirinha (id_cliente, codigo, data_emissao, validade) VALUES
(1, 'CAR-0001', '2026-01-10', '2027-01-10'),
(2, 'CAR-0002', '2026-01-11', '2027-01-11'),
(3, 'CAR-0003', '2026-01-12', '2027-01-12'),
(4, 'CAR-0004', '2026-01-13', '2027-01-13'),
(5, 'CAR-0005', '2026-01-14', '2027-01-14');

INSERT INTO livro (titulo, isbn, sinopse, ano_publicacao) VALUES
('Dom Casmurro', '9788535910663', 'Romance clássico da literatura brasileira.', 1899),
('O Alienista', '9788535910670', 'Novela sobre ciência, loucura e sociedade.', 1882),
('Capitaes da Areia', '9788535910687', 'Obra sobre jovens marginalizados em Salvador.', 1937),
('A Hora da Estrela', '9788535910694', 'Narrativa sobre Macabea e sua vida simples.', 1977),
('Grande Sertao: Veredas', '9788535910700', 'Romance regionalista de grande complexidade literária.', 1956);

INSERT INTO autor (nome, nacionalidade) VALUES
('Machado de Assis', 'Brasileira'),
('Jorge Amado', 'Brasileira'),
('Clarice Lispector', 'Brasileira'),
('Joao Guimaraes Rosa', 'Brasileira'),
('Monteiro Lobato', 'Brasileira');

INSERT INTO categoria (nome, descricao) VALUES
('Romance', 'Obras narrativas longas de ficcao.'),
('Literatura Brasileira', 'Obras de autores brasileiros.'),
('Classico', 'Livros reconhecidos como importantes historicamente.'),
('Conto', 'Narrativas curtas.'),
('Regionalismo', 'Obras ligadas a regioes e culturas locais.');

INSERT INTO edicao (id_livro, numero_edicao, editora, preco_reposicao) VALUES
(1, 1, 'Editora A', 49.90),
(2, 1, 'Editora B', 39.90),
(3, 2, 'Editora C', 59.90),
(4, 1, 'Editora D', 44.90),
(5, 3, 'Editora E', 79.90);

INSERT INTO exemplar (id_edicao, tombo, status_exemplar, data_aquisicao) VALUES
(1, 'TOMBO-001', 'Disponivel', '2026-02-01'),
(2, 'TOMBO-002', 'Disponivel', '2026-02-02'),
(3, 'TOMBO-003', 'Disponivel', '2026-02-03'),
(4, 'TOMBO-004', 'Disponivel', '2026-02-04'),
(5, 'TOMBO-005', 'Disponivel', '2026-02-05');

INSERT INTO livro_autor (id_livro, id_autor) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 4);

INSERT INTO livro_categoria (id_livro, id_categoria) VALUES
(1, 1),
(1, 2),
(2, 4),
(3, 2),
(5, 5);





DELIMITER $$


-- Calcula multa de R$ 2,00 por dia de atraso.
CREATE FUNCTION calcular_multa(
    p_data_prevista DATE,
    p_data_devolucao DATE
)
RETURNS DECIMAL(8,2)
DETERMINISTIC
BEGIN
    DECLARE v_dias_atraso INT DEFAULT 0;
    DECLARE v_multa DECIMAL(8,2) DEFAULT 0.00;

    IF p_data_devolucao IS NOT NULL AND p_data_devolucao > p_data_prevista THEN
        SET v_dias_atraso = DATEDIFF(p_data_devolucao, p_data_prevista);
        SET v_multa = v_dias_atraso * 2.00;
    END IF;

    RETURN v_multa;
END $$



-- Registra empréstimo somente se o exemplar estiver disponível.
CREATE PROCEDURE registrar_emprestimo(
    IN p_id_cliente INT,
    IN p_id_exemplar INT,
    IN p_data_prevista_devolucao DATE
)
BEGIN
    DECLARE v_status VARCHAR(20);

    SELECT status_exemplar
    INTO v_status
    FROM exemplar
    WHERE id_exemplar = p_id_exemplar;

    IF v_status = 'Disponivel' THEN
        INSERT INTO emprestimo (id_cliente, id_exemplar, data_emprestimo, data_prevista_devolucao)
        VALUES (p_id_cliente, p_id_exemplar, NOW(), p_data_prevista_devolucao);

        UPDATE exemplar
        SET status_exemplar = 'Emprestado'
        WHERE id_exemplar = p_id_exemplar;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'O exemplar não está disponível para empréstimo.';
    END IF;
END $$



-- Ao registrar devolução, calcula multa automaticamente e libera o exemplar.
CREATE TRIGGER trg_devolucao_emprestimo
BEFORE UPDATE ON emprestimo
FOR EACH ROW
BEGIN
    IF NEW.data_devolucao IS NOT NULL AND OLD.data_devolucao IS NULL THEN
        SET NEW.multa = calcular_multa(NEW.data_prevista_devolucao, NEW.data_devolucao);
        UPDATE exemplar
        SET status_exemplar = 'Disponivel'
        WHERE id_exemplar = NEW.id_exemplar;
    END IF;
END $$

DELIMITER ;





-- Consulta simplificada de empréstimos com dados do cliente e do livro.
CREATE VIEW vw_emprestimos_detalhados AS
SELECT
    e.id_emprestimo,
    c.nome AS cliente,
    l.titulo AS livro,
    ex.tombo,
    e.data_emprestimo,
    e.data_prevista_devolucao,
    e.data_devolucao,
    e.multa,
    ex.status_exemplar
FROM emprestimo e
INNER JOIN cliente c ON e.id_cliente = c.id_cliente
INNER JOIN exemplar ex ON e.id_exemplar = ex.id_exemplar
INNER JOIN edicao ed ON ex.id_edicao = ed.id_edicao
INNER JOIN livro l ON ed.id_livro = l.id_livro;




-- ROLES
CREATE ROLE IF NOT EXISTS 'role_bibliotecario';

GRANT SELECT, INSERT, UPDATE, DELETE, EXECUTE ON biblioteca.* TO 'role_bibliotecario';

CREATE USER IF NOT EXISTS 'usuario_bibliotecario'@'localhost' IDENTIFIED BY '1234';
CREATE USER IF NOT EXISTS 'usuario_consulta'@'localhost' IDENTIFIED BY '1234';

GRANT 'role_bibliotecario' TO 'usuario_bibliotecario'@'localhost';
GRANT SELECT ON biblioteca.* TO 'usuario_consulta'@'localhost';

SET DEFAULT ROLE 'role_bibliotecario' TO 'usuario_bibliotecario'@'localhost';

FLUSH PRIVILEGES;

CALL registrar_emprestimo(1, 1, '2026-04-10');
call registrar_emprestimo(2, 2, '2026-03-09');
call registrar_emprestimo(3, 3, '2026-03-10');
call registrar_emprestimo(4, 4, '2026-03-11');
call registrar_emprestimo(5, 5, '2026-03-12');


/*UPDATE emprestimo SET data_devolucao = '2026-04-13' WHERE id_emprestimo = 6;*/
SELECT * FROM vw_emprestimos_detalhados;

SELECT * FROM cliente;
SELECT * FROM carteirinha;
SELECT * FROM livro;
SELECT * FROM autor;
SELECT * FROM categoria;
SELECT * FROM edicao;
SELECT * FROM exemplar;
SELECT * FROM emprestimo;
SELECT * FROM livro_autor;
SELECT * FROM livro_categoria;
