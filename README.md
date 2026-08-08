# Desafio Inter

API REST desenvolvida em Java com Spring Boot para gerenciamento de
usuários e cálculo do Dígito Único.

## Tecnologias utilizadas

-   Java 17
-   Spring Boot
-   Spring Web
-   Spring Data JPA
-   H2 Database
-   Maven
-   JUnit 5
-   Mockito
-   JaCoCo
-   Postman

## Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

-   Java 17 ou superior
-   Maven 3.8 ou superior

Verifique as versões instaladas:

``` bash
java --version
mvn --version
```

## 1. Como compilar e executar a aplicação

Clone o repositório:

``` bash
git clone <URL_DO_REPOSITORIO>
```

Entre na pasta do projeto:

``` bash
cd desafioInter
```

Compile o projeto:

``` bash
mvn clean package
```

Para executar a aplicação diretamente pelo Maven:

``` bash
mvn spring-boot:run
```

A aplicação ficará disponível, por padrão, em:

``` text
http://localhost:8080
```

Também é possível executar o arquivo `.jar` gerado após a compilação:

``` bash
java -jar target/desafioInter-*.jar
```

## 2. Como executar os testes unitários

Para executar todos os testes:

``` bash
mvn test
```

Para executar os testes com uma saída mais detalhada:

``` bash
mvn clean test
```

Os testes abrangem as principais camadas da aplicação, incluindo:

-   `UserService`
-   `CriptografiaService`
-   `DigitoUnicoService`
-   `UserController`
-   `DigitoUnicoController`

Também foram implementados testes relacionados ao cache do cálculo do
Dígito Único.

## Cobertura de testes

O projeto utiliza JaCoCo para análise de cobertura.

Para gerar o relatório:

``` bash
mvn clean test jacoco:report
```

Após a execução, o relatório HTML pode ser encontrado em:

``` text
target/site/jacoco/index.html
```

## Funcionalidades

### Usuários

A aplicação disponibiliza endpoints para:

-   Criar usuário
-   Buscar usuário por ID
-   Listar usuários
-   Atualizar usuário
-   Excluir usuário
-   Enviar a chave pública de um usuário
-   Gerar um par de chaves RSA

### Dígito Único

A aplicação permite:

-   Calcular o Dígito Único
-   Associar opcionalmente o cálculo a um usuário
-   Consultar os cálculos realizados para determinado usuário
-   Armazenar os últimos 10 cálculos em cache

Quando um cálculo já está presente no cache, o cálculo novamente não é
executado.

### Criptografia

Os dados de `nome` e `email` dos usuários podem ser criptografados
utilizando RSA 2048.

A chave pública fornecida pelo cliente é utilizada para criptografia,
enquanto a chave privada correspondente pode ser utilizada pelo cliente
para descriptografar os dados.

As chaves são representadas em Base64 para facilitar seu transporte
através da API.

## Principais endpoints

### Usuários

``` text
POST   /users
GET    /users/{id}
GET    /users/listar
PUT    /users/{id}
DELETE /users/{id}
POST   /users/{id}/chave-publica
GET    /users/gerar-chaves
```

### Dígito Único

``` text
POST /users/{userId}/digitos
GET  /users/{userId}/digitos
```

## Exemplo de cálculo

Requisição:

``` http
POST /users/1/digitos
Content-Type: application/json
```

``` json
{
    "numero": 141
}
```

Resultado:

``` text
1 + 4 + 1 = 6
```

Resposta esperada:

``` json
{
    "numero": 141,
    "resultado": 6
}
```

## Testes da API

Uma Collection do Postman pode ser utilizada para testar os endpoints da
aplicação.

Recomenda-se manter a Collection no projeto em:

``` text
docs/postman/
```

Por exemplo:

``` text
docs/
└── postman/
    └── desafioInter.postman_collection.json
```

Após iniciar a aplicação, importe a Collection no Postman e execute os
requests.

## Banco de dados

O projeto utiliza o H2 Database para persistência durante o
desenvolvimento e execução dos testes.

A configuração do banco encontra-se nos arquivos de configuração da
aplicação.

## Observações

Este projeto foi desenvolvido com foco em boas práticas de organização,
separação de responsabilidades e testes automatizados.

A aplicação possui testes unitários para os principais serviços e
controllers, além de testes específicos para validar o comportamento do
cache e da criptografia.