# API de Pagamentos

## 1. Objetivo do projeto

Este projeto consiste na implementação de uma **API REST de Pagamentos**
para um cenário bancário, no contexto da área de cartões de crédito.
O desafio propõe a construção de uma aplicação capaz de processar operações de 
pagamento, consulta e estorno, seguindo padrões REST.


---

## 2. Funcionalidades implementadas

A aplicação foi desenvolvida para atender às funcionalidades solicitadas no desafio.

### Pagamento

Permite registrar uma nova transação de pagamento.

### Consulta de transações

Permite consultar todas as transações ou uma transação específica por ID.

### Estorno

Permite realizar o estorno de uma transação existente a partir do seu identificador.

---

## 3. Tecnologias utilizadas

A aplicação foi construída com foco em uma stack Java moderna e amplamente utilizada em APIs corporativas.

### Backend

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Validation
* Spring Security

### Banco de dados

* PostgreSQL

### Persistência e auditoria

* Hibernate / JPA
* Hibernate Envers para auditoria de alterações

### Documentação e testes

* Springdoc OpenAPI / Swagger
* Postman Collection incluída no projeto para facilitar os testes dos endpoints

### Build e empacotamento

* Maven

### Infraestrutura

* Docker

---

## 4. Organização da aplicação

A estrutura da aplicação foi separada em camadas para facilitar entendimento, manutenção e evolução:

* **Rest controller**: Responsável por expor os endpoints da API

* **Service**: Contém as regras de negócio

* **Repository**: Camada de acesso aos dados

* **Domain**: Representa as entidades persistidas

* **Dto**: Objetos de entrada e saída da API

* **Exception handler**: Centraliza o tratamento de erros da aplicação

* **Enumeration**: Define enums de status da transação e tipo de pagamento

As classes e métodos foram documentados com JavaDoc.

---

## 5. Endpoints principais

Os endpoints implementados estão centralizados no contexto da API de transações.

### Realizar pagamento

`POST /pagamentos-api/transacoes/pagar`

### Consultar todas as transações

`GET /pagamentos-api/transacoes`

### Consultar transação por ID

`GET /pagamentos-api/transacoes/{id}`

### Estornar transação

`POST /pagamentos-api/transacoes/{id}/estorno`

---

## 6. Padrão de respostas e erros

A API foi estruturada para responder com conteúdo JSON, utilizando códigos HTTP adequados, como por exemplo:

* `200 OK`
* `400 Bad Request`
* `404 Not Found`
* `500 Internal Server Error`

Também foi implementado tratamento centralizado para:

* erros de validação de campos
* payload JSON inválido
* valores inválidos para enums
* recursos não encontrados
* erros inesperados

Isso torna a API mais previsível e facilita o consumo por clientes externos.

---

## 7. Configuração da aplicação

A configuração do projeto é realizada por meio do arquivo `application.properties`.

---

## 8. Como executar o projeto localmente

### Pré-requisitos

Antes de subir a aplicação, é necessário ter instalado:

* Java 17+
* Maven 3.9+
* Docker
* Docker Compose

### Execução local sem Docker

1. Clone o repositório:

```bash
git clone https://github.com/eliasfs06/ToolsChallenge
```

2. Acesse a pasta do projeto:

```bash
cd ToolsChallenge
```

3. Garanta que o PostgreSQL esteja disponível e configurado conforme o `application.properties`.

4. Execute a aplicação:

```bash
./mvnw spring-boot:run
```

Ou, no Windows:

```bash
mvnw.cmd spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080/pagamentos-api
```

---

## 9. Como subir a aplicação com Docker

A aplicação também pode ser executada com Docker, utilizando os arquivos de infraestrutura adicionados ao projeto.

### Subindo com Docker Compose

Na raiz do projeto, execute:

```bash
docker compose up --build
```

Esse processo irá:

* subir o banco PostgreSQL
* construir a imagem da aplicação
* iniciar a API já conectada ao banco

### Derrubando os containers

```bash
docker compose down
```

### Derrubando e removendo volumes

```bash
docker compose down -v
```

### Endereço da aplicação

Após a subida, a API estará disponível em:

```text
http://localhost:8080/pagamentos-api
```

---

## 10. Documentação da API

Com a aplicação em execução, a documentação Swagger pode ser acessada em:

```text
http://localhost:8080/pagamentos-api/swagger-ui/index.html
```

A especificação OpenAPI também pode ser consultada em:

```text
http://localhost:8080/pagamentos-api/v3/api-docs
```

---

## 11. Como testar a aplicação

A forma recomendada de testar a API é utilizando a **collection do Postman** disponibilizada no projeto.

### O que pode ser testado pela collection

A collection contempla os fluxos principais da API:

* realizar pagamento
* consultar todas as transações
* consultar transação por ID
* realizar estorno

### Como usar

1. Abra o Postman
2. Importe a collection presente no projeto
3. Ajuste a variável `baseUrl`, se necessário
4. Execute as requisições na ordem desejada

### Fluxo sugerido para validação

1. Criar um pagamento
2. Consultar a transação criada por ID
3. Listar todas as transações
4. Estornar a transação criada
5. Consultar novamente o registro estornado

Esse fluxo permite validar o comportamento principal esperado para a API.

---

## 12. Testes unitários

Além dos testes manuais, o projeto contempla testes unitários para validar as principais regras de negócio da aplicação de forma isolada.

### Tecnologias utilizadas nos testes

Para a implementação dos testes unitários, foram utilizadas as seguintes ferramentas:

* JUnit 5: para estruturação e execução dos testes
* Mockito: para simulação de dependências e isolamento das classes testadas

### Como executar os testes unitários

Para executar os testes do projeto, utilize o comando abaixo na raiz da aplicação:

```bash
./mvnw test
```

Ou, no Windows:

```bash
mvnw.cmd test
```

Essa etapa permite validar rapidamente se as regras principais da aplicação funcionam conforme esperado.

---

## 13. Auditoria

O projeto foi preparado para uso de auditoria com **Hibernate Envers**, permitindo rastrear alterações realizadas nas entidades auditadas.

Configurações importantes adotadas:

* schema de auditoria: `auditoria`
* sufixo das tabelas de auditoria: `_AUD`
* campos de revisão customizados

Essa abordagem facilita rastreabilidade e manutenção histórica dos dados, o que é bastante relevante em sistemas financeiros.

---

## 14. Decisões de implementação

Algumas decisões foram tomadas para deixar a solução mais robusta e próxima de um cenário real:

* uso de DTOs para separar entrada/saída da camada de persistência
* enums para restringir valores válidos de status e forma de pagamento
* tratamento global de exceções
* validação declarativa com annotations
* documentação via Swagger/OpenAPI
* containerização para facilitar execução
* auditoria com Envers
* organização em camadas para facilitar evolução futura

---
