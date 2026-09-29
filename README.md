# Movie Catalog API

[![CI](https://github.com/Gudoourado/movie-catalog-api/actions/workflows/ci.yml/badge.svg)](https://github.com/Gudoourado/movie-catalog-api/actions/workflows/ci.yml)

API REST para catálogo de filmes com sistema de avaliações, desenvolvida com **Java 17** e **Spring Boot 3.2**.

## Tecnologias

- Java 17, Spring Boot 3.2.4, Spring Data JPA, PostgreSQL (H2 em memória no perfil `dev`), Bean Validation, Maven

## Como Executar

Pré-requisitos: Java 17 ou mais recente e Maven.

```bash
git clone https://github.com/Gudoourado/movie-catalog-api.git
cd movie-catalog-api
```

### Rápido, sem instalar banco (perfil `dev`)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Usa um banco H2 em memória: sobe em segundos e os dados somem quando a aplicação para.

- API: http://localhost:8081
- Console do H2: http://localhost:8081/h2-console (JDBC URL `jdbc:h2:mem:moviedb`, usuário `sa`, sem senha)

### Com PostgreSQL

Crie o banco `moviedb` e rode:

```bash
mvn spring-boot:run
```

Usuário e senha vêm das variáveis `DB_USERNAME` e `DB_PASSWORD` (padrão: `postgres` / `postgres`).

## Testes

```bash
mvn test
```

Testes da camada web (`@WebMvcTest`, com o serviço simulado) que conferem o status HTTP e a mensagem de cada tipo de erro: JSON mal formado, parâmetro inválido ou ausente, método e content-type errados, endereço inexistente, validação e erro inesperado sem expor detalhe interno.

## Endpoints - Filmes

| Método | Endpoint                 | Descrição                |
|--------|--------------------------|--------------------------|
| GET    | /api/movies              | Listar filmes            |
| GET    | /api/movies/{id}         | Buscar filme (com reviews)|
| POST   | /api/movies              | Criar filme              |
| PUT    | /api/movies/{id}         | Atualizar filme          |
| DELETE | /api/movies/{id}         | Deletar filme            |
| GET    | /api/movies/genre/{genre}| Filtrar por gênero       |
| GET    | /api/movies/search?keyword=| Busca por palavra-chave|
| GET    | /api/movies/top-rated    | Filmes mais bem avaliados|

## Endpoints - Avaliações

| Método | Endpoint                       | Descrição          |
|--------|--------------------------------|--------------------|
| POST   | /api/movies/{movieId}/reviews  | Adicionar avaliação|
| GET    | /api/movies/{movieId}/reviews  | Listar avaliações  |
| DELETE | /api/movies/reviews/{reviewId} | Deletar avaliação  |

## Exemplo - Criar Filme

```json
{
  "title": "Interestelar",
  "description": "Uma equipe de exploradores viaja através de um buraco de minhoca",
  "director": "Christopher Nolan",
  "releaseYear": 2014,
  "durationMinutes": 169,
  "genre": "FICCAO_CIENTIFICA"
}
```

## Autor

**Gustavo Dourado** - Desenvolvedor Backend Jr
