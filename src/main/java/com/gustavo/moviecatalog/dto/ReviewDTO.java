package com.gustavo.moviecatalog.dto;

import jakarta.validation.constraints.*;

public class ReviewDTO {

    private Long id;

    @NotBlank(message = "Nome do autor é obrigatório")
    @Size(max = 100)
    private String author;

    @NotNull(message = "Nota é obrigatória")
    @Min(value = 1, message = "Nota mínima é 1")
    @Max(value = 5, message = "Nota máxima é 5")
    private Integer rating;

    @Size(max = 500)
    private String comment;

    private Long movieId;

    public ReviewDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
}
