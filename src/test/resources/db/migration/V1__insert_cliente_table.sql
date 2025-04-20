
CREATE TABLE Cliente (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    inclucao TIMESTAMP NOT NULL
);

CREATE SEQUENCE cliente_seq
    INCREMENT BY 1
    START WITH 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


INSERT INTO Cliente (id,nome, email, cpf, inclucao)
VALUES (nextval('cliente_seq'),'João Silva', 'joao.silva@email.com', '95136137045', CURRENT_TIMESTAMP);
INSERT INTO Cliente (id,nome, email, cpf, inclucao)
VALUES (nextval('cliente_seq'),'Maria Silva', 'maria.silva@email.com', '12345678700', CURRENT_TIMESTAMP);