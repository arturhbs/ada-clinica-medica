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

![Arquitetura Sistema](/Arquitetura%20Clínica%20Médica.png)

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

---

## 9. Camada de Infraestrutura com Nginx

Além da aplicação backend, foi adicionada uma camada de infraestrutura utilizando **Nginx como proxy reverso**, responsável por intermediar todas as requisições HTTP antes de chegarem à API.

A aplicação não é exposta diretamente na porta interna do container, sendo acessada exclusivamente por meio do Nginx na porta 80.

---

### 9.1 Proxy Reverso

O Nginx atua como gateway de entrada da aplicação, recebendo as requisições na porta 80 e encaminhando-as internamente para o serviço da API dentro da rede Docker.

Essa abordagem permite centralizar regras de segurança, controle de tráfego e tratamento de erros fora do código da aplicação.

---

### 9.2 Cabeçalhos de Segurança

Foram configurados cabeçalhos HTTP adicionais para reforçar a segurança das respostas:

- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY`
- `X-XSS-Protection: 1; mode=block`

Esses cabeçalhos são adicionados pelo Nginx em todas as respostas, independentemente do status retornado.

---

### 9.3 Controle de Requisições

Foi implementado controle de taxa de requisições por endereço IP:

- Limite de 5 requisições por segundo
- Permissão de pico temporário (burst) de até 10 requisições
- Retorno automático de HTTP 429 quando o limite é excedido

Essa configuração evita abuso da API e protege o serviço contra sobrecarga.

---

### 9.4 Limite de Tamanho de Requisição

Foi definido limite máximo de 1MB para o corpo das requisições HTTP.

Caso o tamanho seja excedido, o Nginx retorna:

- HTTP 413 – Request Entity Too Large

Essa validação ocorre antes da requisição chegar à aplicação.

---

### 9.5 Compressão de Respostas

A compressão GZIP foi habilitada para respostas do tipo:

- `application/json`
- `text/plain`

Quando o cliente envia o cabeçalho `Accept-Encoding: gzip`, o Nginx comprime a resposta e adiciona:

- `Content-Encoding: gzip`

Isso reduz o tamanho do tráfego e melhora a eficiência da comunicação.

---

### 9.6 Logs Estruturados

Foi configurado formato personalizado de log no Nginx, registrando:

- Endereço IP do cliente
- Método HTTP
- URI acessada
- Status retornado
- Tempo de resposta da aplicação (upstream)
- Tempo total da requisição

Essas informações são exibidas diretamente nos logs do container.

---

### 9.7 Proteção do Swagger

O acesso ao Swagger UI e à documentação OpenAPI foi protegido com autenticação básica (Basic Auth).

Ao acessar:

- `/swagger-ui/`
- `/v3/api-docs`

é solicitado usuário e senha configurados no Nginx.

Os demais endpoints da API permanecem acessíveis normalmente.

---

### 9.8 Páginas de Erro Personalizadas

Foram configuradas páginas customizadas para:

- Erro 404 (recurso não encontrado)
- Erros 5xx (falhas internas ou indisponibilidade da API)

O Nginx intercepta erros da aplicação e retorna uma página HTML personalizada, mantendo o código HTTP original.


## 10. Como Testar as Funcionalidades de Infraestrutura

Alguns exemplos de testes que podem ser realizados:

### Testar controle de requisições

```bash
for i in {1..20}; do curl -o /dev/null -s -w "%{http_code}\n" http://localhost/pacientes; done
```
Deve retornar HTTP 429 após exceder o limite

### Testar limite de tamanho da requisição

Envia um corpo maior que 1MB em uma requisição POST
Retorno deve ser HTTP 413

### Testar compressão

```bash
curl -I -H "Accept-Encoding: gzip" http://localhost/pacientes
```

Deve aparecer no header
```
Content-Encoding: gzip
```

### Testar compressão
Será solicitado o usuário e senha ao acessar 
```
http://localhost/swagger-ui/index.html
```

### Testar páginas de erro
- Acessar uma rota inexistente → deve retornar página 404 personalizada.
- Parar o container da API e acessar um endpoint → deve retornar página 50x personalizada.