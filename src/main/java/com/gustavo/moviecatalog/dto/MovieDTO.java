package com.gustavo.moviecatalog.dto;

import com.gustavo.moviecatalog.model.Genre;
import jakarta.validation.constraints.*;
import java.util.List;

public class MovieDTO {

    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 200)
    private String title;

    private String description;

    @NotBlank(message = "Diretor é obrigatório")
    private String director;

    @Min(value = 1888)
    @Max(value = 2030)
    private Integer releaseYear;

    private Integer durationMinutes;

    @NotNull(message = "Gênero é obrigatório")
    private Genre genre;

    private Double averageRating;
    private Integer totalReviews;
    private List<ReviewDTO> reviews;

    public MovieDTO() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

    public Integer getTotalReviews() { return totalReviews; }
    public void setTotalReviews(Integer totalReviews) { this.totalReviews = totalReviews; }

    public List<ReviewDTO> getReviews() { return reviews; }
    public void setReviews(List<ReviewDTO> reviews) { this.reviews = reviews; }
}
