# Sistema de Gerenciamento de Clínica Médica

## 1. Introdução

Este projeto tem como objetivo o desenvolvimento de uma API REST para o gerenciamento de uma clínica médica, contemplando o cadastro de pacientes, médicos e o agendamento de consultas.

---

## 2. Visão Geral da Solução

A solução consiste em uma aplicação backend desenvolvida em Java, que expõe endpoints REST para consumo via HTTP. O acesso e teste da API são realizados por meio do Swagger UI.

O sistema permite:
- Cadastro e consulta de pacientes
- Cadastro e consulta de médicos
- Agendamento de consultas médicas
- Validação de regras de negócio. Exemplo: unicidade de CPF e CRM; conflitos de horário.

---

## 3. Arquitetura do Sistema

A arquitetura adotada é **Monolítica Modular por Domínio**.

Apesar de ser uma aplicação monolítica (um único deploy), o código foi organizado em módulos de domínio bem definidos, de forma a reduzir acoplamento e facilitar manutenção e evolução futura. Como o caso para uma futura evolução para SOA ou Microserviços.

Cada domínio do sistema é isolado, em que possuem sua própria lógica e independem, assim como o suas tabelas, uma das outras:
- Paciente
- Médico
- Agenda (Consultas)

### 3.1 Justificativa da Arquitetura

A escolha por um monólito modular se deu pelos seguintes motivos:
- Apenas uma pessoa trabalhando no projeto;
- Simplicidade de desenvolvimento e testes;
- Menor complexidade operacional em relação as outras arquiteturas;
- Facilidade de evolução futura para arquiteturas distribuídas, se necessário.

---

## 4. Organização Interna dos Módulos

Cada módulo segue a mesma estrutura interna:

- **Controller**: responsável por receber requisições HTTP e retornar respostas REST
- **Service**: responsável pelas regras de negócio
- **Repository**: responsável pela persistência de dados (JPA)
- **DTOs**: responsáveis pelo contrato de entrada e saída da API
- **Entity**: representação das entidades do domínio

---

## 5. Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- H2 Database (teste ambiente local)
- Docker e Docker Compose
- Swagger / OpenAPI
- Maven
- JUnit 5 e Mockito

---

## 6. Regras de Negócio Implementadas

- O CPF do paciente deve ser único
- O CRM do médico deve ser único
- Não é permitido agendar consultas no passado
- Não é permitido conflito de horário para o mesmo médico
- Validação de dados de entrada utilizando Bean Validation
- Tratamento padronizado de erros da API

---

## 7. Endpoints Disponíveis

### Pacientes
- `POST /pacientes`
- `GET /pacientes`
- `GET /pacientes/{id}`

### Médicos
- `POST /medicos`
- `GET /medicos`
- `GET /medicos/{id}`

### Consultas
- `POST /consultas`
- `GET /consultas`
- `GET /consultas/{id}`

---

## 8. Execução do Projeto

### 8.1 Execução Local (H2)

Requisitos:
- Java 21
- Maven

Comando:
```bash
./mvnw spring-boot:run
```
### 8.2 Execução com Docker (PostgreSQL)
Requisitos:
- Docker
- Docker Compose

Comando
```bash
docker compose up --build
```