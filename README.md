# Movie Catalog API

API REST para catálogo de filmes com sistema de avaliações, desenvolvida com **Java 17** e **Spring Boot 3.2**.

## Tecnologias

- Java 17, Spring Boot 3.2.4, Spring Data JPA, PostgreSQL / H2, Bean Validation, Maven

## Como Executar

```bash
git clone https://github.com/seu-usuario/movie-catalog-api.git
cd movie-catalog-api
./mvnw spring-boot:run
# API em http://localhost:8081
```

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
