# CRUD de Produtos - Java Spark + PostgreSQL
Projeto prático desenvolvido para consolidar conceitos de Back-end e persistência de dados. A ideia foi criar uma API simples e funcional para gerenciar produtos, aplicando padrões de organização que facilitam a manutenção do código.

Optei pelo Spark Java para compreender o funcionamento interno de uma aplicação antes de utilizar as abstrações do Spring Boot. Essa escolha permitiu configurar manualmente as rotas, a conexão JDBC e o tratamento de JSON, consolidando os fundamentos de uma API REST.
🛠️ Tecnologias
Java 17 e Spark Java (Microframework).

PostgreSQL com integração via JDBC.

Gson para conversão de dados em JSON.

Front-end: HTML, JS (Fetch API) e Bootstrap.

## 🏗️ Organização

DAO: Toda a parte de SQL e conexão com o banco.

Service: Onde as rotas da API são configuradas.

Model: A classe que representa o produto no sistema.

## 🚀 Como rodar
Banco de Dados: Crie o banco exercicio_spark e rode o script que está na pasta /sql.

Java: Rode a classe Main.java. O servidor vai subir na porta 4567.

Acesse: Abra http://localhost:4567 no navegador.
