# C07-TRABALHO - Sistema de Biblioteca

Projeto Java com JDBC e MySQL para gerenciamento de biblioteca.

## Tecnologias

- Java 17
- Maven
- MySQL
- MySQL Workbench

## Como preparar o banco

1. Abra o MySQL Workbench.
2. Execute o arquivo `biblioteca.sql`.
3. O script cria o banco `biblioteca`, tabelas, inserts, view, function, procedure, trigger e usuarios.

Usuario usado pela aplicacao:

```text
usuario_bibliotecario
senha: 1234
```

## Como rodar o projeto

1. Abra o projeto no IntelliJ IDEA.
2. Garanta que o Maven importou a dependencia `mysql-connector-j`.
3. Execute a classe `org.example.Main`.
4. Use o menu do terminal para inserir, alterar, deletar e consultar os dados.

## Observacoes

- O cadastro de emprestimo chama a procedure `registrar_emprestimo`.
- A devolucao aciona o trigger `trg_devolucao_emprestimo`, que calcula a multa e libera o exemplar.
- As consultas de livros, clientes, carteirinhas, edicoes, exemplares e emprestimos usam os DAOs em `src/main/java/biblioteca/dao`.
