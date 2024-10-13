# Kaffa Mobile - Pre-qualification Test

**Vaga:** Desenvolvedor Back-End JR (Java/Kotlin)

**Descrição da vaga:**  
Realizar as atividades de análise, desenvolvimento, manutenção, implantação, sustentação e documentação de software
garantindo a qualidade das entregas.

**Responsabilidades e atribuições:**

- Desenvolvimento de sistemas;
- Gerar e gerenciar releases no GitHub;
- Apoiar o time no desenvolvimento das atividades do projeto.

**Requisitos e qualificações:**

- Ensino superior completo ou cursando;

**Habilidades Necessárias:**

- Experiência com duas ou mais das seguintes tecnologias: Kotlin, Java, Android, React, TypeScript e SQL;
- Análise crítica e capacidade em resolução de problemas.

**Habilidades Desejáveis:**

- Conhecimento em NodeJs, React e outros frameworks de mercado;
- Experiência com integração contínua e TDD;
- Experiência com modelagem e consumo de APIs REST.

**Diferenciais:**

- Experiência com arquitetura de soluções;
- Liderança de times de desenvolvimento de software/produto;
- Experiência com desenvolvimento de soluções GIS;
- Experiência com GIT;
- Micro serviço.

---

Este repositório contém a solução para os exercícios propostos no teste técnico da Kaffa Mobile. As soluções foram
desenvolvidas utilizando Java para a validação de CNPJ e outros exercícios conforme descritos abaixo. Cada exercício foi
implementado para ser facilmente executado e testado.

### Lista de Exercícios:

1) Validate CNPJ format and check digits
2) Test if two rectangles intersect
3) Compute the area of intersection between two rectangles
4) Simple Todo List
5) Rest Client - World Clock
6) Rest Server - World Clock
7) Entity Relationship Diagram - Simple Order Manager

## Requisitos

Para rodar o projeto, certifique-se de ter instalado:

- Java JDK 23

## Como rodar

1. Clone este repositório:
   ```bash
   git clone https://github.com/viniciusdsandrade/technical-test-java-jr-codex-utilities
   cd seu_repositorio
   ```

## Exercícios

### 1) Validação de CNPJ

Este exercício consiste em validar se uma string está no formato correto de CNPJ e verificar seus dígitos verificadores
conforme o algoritmo definido pela Receita Federal.

#### Exemplo de Códigos:

```java
import static java.lang.Character.getNumericValue;

public class CNPJValidator {
    // Metodo de validação de CNPJ
    public static boolean validarCNPJ(String cnpj) {
        String cnpjNumeros, cnpjBase, digitoVerificadorCalculado, digitoVerificadorOriginal;

        // Remove todos os caracteres não numéricos
        cnpjNumeros = cnpj.replaceAll("\\D", "");

        // Verifica se o CNPJ tem 14 dígitos
        if (cnpjNumeros.length() != 14) {
            return false;
        }

        // Verifica se os dígitos verificadores são válidos
        cnpjBase = cnpjNumeros.substring(0, 12);
        digitoVerificadorCalculado = calcularDigitosVerificadores(cnpjBase);
        digitoVerificadorOriginal = cnpjNumeros.substring(12, 14);

        return digitoVerificadorCalculado.equals(digitoVerificadorOriginal);
    }

    private static String calcularDigitosVerificadores(String cnpjBase) {
        // Implementação do cálculo de dígitos verificadores...
    }
}
```

#### Como testar:

Para testar a validação de CNPJs, execute o método `main` com uma lista de CNPJs formatados e não formatados. O programa
retornará se cada CNPJ é válido ou não.

#### Exemplo de Saída:

```bash
CNPJ  1 (12.345.678/0001-95) é válido
CNPJ  2 (12345678000195) é válido
CNPJ  3 (48.724.911/0001-99) é inválido
...
```

#### Screenshots

Aqui estão alguns exemplos do código em execução:
![Validação CNPJ](screenshot)

### 2) Teste de Interseção entre Retângulos

Neste exercício, foram definidos três retângulos em um grid discreto, e a tarefa é verificar se eles se intersectam,
considerando seus vértices. Um retângulo no formato (0, 0; 1, 1) terá uma área de 4 unidades.

#### Exemplo de Código:

```java
import geometry.Retangulo;

import static geometry.Retangulo.intersects;

/*
    2) Test if two rectangles intersect

    Considering two rectangles in a discrete grid (like pixels in a display), each defined by two points, return
    true if they intersect, false otherwise.
    Note: the points are included in the rectangle and have a dimension of 1 unit; the rectangle (0, 0; 1, 1) has an
    area of 4 units.

    Example:
        |..........+---+
        |..........|.C.|
        |..+-------#---+
      10|..|.......|....
        |..|...A...|....
        |..|.......|....
        |..|...#####-+..
        |..|...#####.|..
      5 |..+---#####.|..
        |......|..B..|..
        |......|.....|..
        |......+-----+..
      1 |...............
      0 +----|----|----|
        0    5    10   15

        A = (3, 5; 11, 11)
        B = (7, 2; 13, 7)
        C = (11, 11; 15, 13)

        intersects(A, B) => true
        intersects(A, C) => true
        intersects(B, C) => false
 */

public static void main(String[] ignoredArgs) {
    // Definindo os retângulos A, B e C
    Retangulo A = new Retangulo(3, 5, 11, 11);
    Retangulo B = new Retangulo(7, 2, 13, 7);
    Retangulo C = new Retangulo(11, 11, 15, 13);

    // Testando as interseções
    System.out.println("intersects(A, B): " + intersects(A, B)); // true
    System.out.println("intersects(A, C): " + intersects(A, C)); // true
    System.out.println("intersects(B, C): " + intersects(B, C)); // false
}
```

#### Como testar:

Para testar a interseção entre os retângulos, execute o código acima. O programa exibirá o resultado de cada teste de
interseção entre os retângulos definidos.

#### Exemplo de Saída:

```bash
intersects(A, B): true
intersects(A, C): true
intersects(B, C): false
```

#### Screenshots

Aqui estão alguns exemplos do código em execução:
![Interseção Retângulos](screenshot)

### 3) Cálculo da Área de Interseção entre Retângulos

Este exercício consiste em calcular a área de interseção entre dois retângulos em um grid discreto. Cada retângulo é
definido por dois pontos, e a área é computada considerando que cada ponto tem dimensão de 1 unidade.

#### Exemplo de Código:

```java
import geometry.Retangulo;

import static geometry.Retangulo.areaOfIntersection;

/*
3) Compute the area of intersection between two rectangles

    Considering two rectangles in a discrete grid (like pixels in a display), each defined by two points, compute
    the area of intersection between the two.

    Note: the points are included in the rectangle and have a dimension of 1 unit; the rectangle (0, 0; 1, 1) has an
    area of 4 units.

    Example:
    |..........+---+
    |..........|.C.|
    |..+-------#---+
  10|..|.......|....
    |..|...A...|....
    |..|.......|....
    |..|...#####-+..
    |..|...#####.|..
  5 |..+---#####.|..
    |......|..B..|..
    |......|.....|..
    |......+-----+..
  1 |...............
  0 +----|----|----|
    0    5   10    15

    A = (3, 5; 11, 11)
    B = (7, 2; 13, 7)
    C = (11, 11; 15, 13)

    areaOfIntersection(A, B) = 15
    areaOfIntersection(A, C) = 1
 */

public static void main(String[] ignoredArgs) {

    // Exemplo de uso
    Retangulo A = new Retangulo(3, 5, 11, 11);
    Retangulo B = new Retangulo(7, 2, 13, 7);
    Retangulo C = new Retangulo(11, 11, 15, 13);

    System.out.println("Área de interseção entre A e B: " + areaOfIntersection(A, B)); // 15
    System.out.println("Área de interseção entre A e C: " + areaOfIntersection(A, C)); // 1
}
```

#### Como testar:

Para testar o cálculo da área de interseção entre os retângulos, execute o código acima. O programa exibirá o valor da
área de interseção para cada par de retângulos.

#### Exemplo de Saída:

```bash
Área de interseção entre A e B: 15
Área de interseção entre A e C: 1
```

#### Screenshots

Aqui estão alguns exemplos do código em execução:
![Área de Interseção Retângulos](screenshot)

### 4) Simple Todo List

Este exercício consiste em criar uma aplicação de lista de tarefas que permite a criação e exclusão de tarefas. A
aplicação deve persistir as tarefas entre execuções e pode utilizar qualquer tipo de armazenamento, como banco de dados,
arquivos ou PaaS (Firebase, etc).

#### Exemplo de Código:

```java
package com.restful.todo.list.controller;

import com.restful.todo.list.dto.TaskCreateDTO;
import com.restful.todo.list.dto.TaskDetailDTO;
import com.restful.todo.list.dto.TaskUpdateDTO;
import com.restful.todo.list.entity.Task;
import com.restful.todo.list.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/v1/task")
@Tag(name = "Task Controller", description = "Controller para gerenciamento de tarefas da Todo List")
public class TaskController {
    // Controller e endpoints...
}
```

#### Persistência de Tarefas:

A persistência das tarefas foi implementada utilizando um banco de dados relacional com JPA, através da entidade `Task`
e o repositório `TaskRepository`.

#### Como testar:

1. Utilize o Postman ou outro cliente REST para criar, atualizar e excluir tarefas através dos endpoints.
2. Persistência entre execuções pode ser verificada reiniciando a aplicação e consultando as tarefas novamente.

#### Exemplo de Saída:

```bash
POST /api/v1/task
{
    "description": "Comprar pão",
    "done": false
}
Resposta: 201 Created

GET /api/v1/task/1
Resposta: 200 OK
{
    "id": 1,
    "description": "Comprar pão",
    "done": false
}

PATCH /api/v1/task/1
{
    "done": true
}
Resposta: 200 OK

DELETE /api/v1/task/1
Resposta: 204 No Content
```

#### Screenshots

Aqui estão alguns exemplos da aplicação em execução:
![Todo List API](screenshot)

### 5) Rest Client - World Clock

Este exercício consiste em criar um cliente REST que consulta um servidor e exibe a data/hora atual nos fusos horários
local e UTC.

#### Exemplo de Código:

```java
package com.restful.rest.client.world.clock.controller;

import com.restful.rest.client.world.clock.exception.WorldClockException;
import com.restful.rest.client.world.clock.model.WorldClockResponse;
import com.restful.rest.client.world.clock.service.WorldClockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static java.time.ZoneId.systemDefault;
import static java.time.ZonedDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

@Controller
public class WorldClockController {
    // Controller e endpoints...
}
```

#### Como testar:

1. Acesse a página inicial para visualizar o horário formatado em UTC e local.
2. Para consultar a data/hora no formato JSON, faça uma requisição GET para `/api/time`.

#### Exemplo de Saída:

```bash
GET /api/time
Resposta: 200 OK
{
    "utcTime": "2024-10-04T14:40:00Z",
    "localTime": "2024-10-04T11:40:00-03:00"
}
```

#### Screenshots

Aqui estão alguns exemplos da aplicação em execução:
![World Clock API](screenshot)

### 6) Rest Server - World Clock

Este exercício consiste em criar um servidor REST que retorna um JSON com o horário atual em UTC, no formato ISO-8601.

#### Exemplo de Código:

```java
package com.restful.rest.server.world.clock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static java.time.ZoneOffset.UTC;
import static java.time.ZonedDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_INSTANT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController("WorldClockController")
public class WorldClockController {
    @GetMapping(value = "/api/time/utc", produces = APPLICATION_JSON_VALUE)
    public Map<String, String> getCurrentUtcTime() {
        ZonedDateTime utcNow = now(UTC);
        String formattedUtcTime = utcNow.format(ISO_INSTANT);
        Map<String, String> response = new HashMap<>();
        response.put("currentDateTime", formattedUtcTime);

        return response;
    }
}
```

#### Como testar:

Faça uma requisição GET para o endpoint `/api/time/utc` e o servidor retornará a data/hora atual em UTC no formato JSON.

#### Exemplo de Saída:

```bash
GET /api/time/utc
Resposta: 200 OK
{
    "currentDateTime": "2024-10-04T14:40:00Z"
}
```

#### Screenshots

Aqui estão alguns exemplos da aplicação em execução:
![World Clock Rest Server](screenshot)

### 7) Entity Relationship Diagram - Simple Order Manager

Este exercício consiste em modelar um sistema simples de gerenciamento de pedidos, incluindo as tabelas de Clientes,
Produtos, Pedidos, Itens de Pedido e Pagamentos. Abaixo está o SQL que descreve o modelo de relacionamento entre essas
entidades.

#### Código MySQL:

```mysql
DROP DATABASE IF EXISTS db_simple_order_manager;
CREATE DATABASE IF NOT EXISTS db_simple_order_manager;
USE db_simple_order_manager;

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS tb_clients
(
    id      BIGINT UNSIGNED AUTO_INCREMENT,
    name    VARCHAR(100)        NOT NULL,
    email   VARCHAR(100) UNIQUE NOT NULL,
    phone   VARCHAR(15)         NULL,
    address VARCHAR(255)        NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS tb_products
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    name           VARCHAR(100)   NOT NULL,
    description    TEXT           NULL,
    price          DECIMAL(10, 2) NOT NULL,
    stock_quantity INT UNSIGNED   NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

-- Tabela de Pedidos
CREATE TABLE IF NOT EXISTS tb_orders
(
    id           BIGINT UNSIGNED AUTO_INCREMENT,
    client_id    BIGINT UNSIGNED                                      NOT NULL,
    order_date   DATE                                                 NOT NULL,
    status       ENUM ('pending', 'shipped', 'delivered', 'canceled') NOT NULL,
    total_amount DECIMAL(10, 2) DEFAULT 0.00                          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES tb_clients (id)
);

-- Tabela de Itens do Pedido
CREATE TABLE IF NOT EXISTS tb_order_items
(
    id         BIGINT UNSIGNED AUTO_INCREMENT,
    order_id   BIGINT UNSIGNED             NOT NULL,
    product_id BIGINT UNSIGNED             NOT NULL,
    quantity   INT UNSIGNED                NOT NULL,
    price      DECIMAL(10, 2) DEFAULT 0.00 NOT NULL, -- Subtotal calculado pelo trigger
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES tb_orders (id),
    FOREIGN KEY (product_id) REFERENCES tb_products (id)
);

-- Tabela de Pagamentos
CREATE TABLE IF NOT EXISTS tb_payments
(
    id             BIGINT UNSIGNED AUTO_INCREMENT,
    order_id       BIGINT UNSIGNED                                         NOT NULL,
    payment_date   DATE                                                    NOT NULL,
    amount         DECIMAL(10, 2) DEFAULT 0.00                             NOT NULL, -- Valor calculado pelo trigger
    payment_method ENUM ('credit_card', 'paypal', 'bank_transfer', 'cash') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (order_id) REFERENCES tb_orders (id)
);
```

#### Como testar:

Embora o exercício seja apenas a modelagem do banco de dados, você pode testar a criação das tabelas e relacionamentos
executando o SQL acima em um banco de dados MySQL.
A orderem sugerida é:
1 - 'db_simple_order_manager';
2 - 'rules_db_simple_order_manager';
3 - 'seed_db_simple_order_manager';

#### Extras:

Aqui está o SQL para listar os pedidos com o número de itens:

```mysql
CREATE VIEW vw_orders_with_item_count AS
SELECT o.id         AS order_id,
       COUNT(oi.id) AS number_of_items,
       o.total_amount,
       o.order_date,
       o.status
FROM tb_orders o
         JOIN tb_order_items oi ON o.id = oi.order_id
GROUP BY o.id;
```

#### Índices:

- **idx_orders_client_id**: acelera consultas de pedidos por cliente.
- **idx_order_items_order_id**: melhora a busca por itens de pedidos.
- **idx_order_items_product_id**: acelera consultas relacionadas a produtos.
- **idx_payments_order_id**: melhora o desempenho de consultas de pagamentos.
- **idx_clients_email**: garante a unicidade do email dos clientes e acelera buscas por email.

