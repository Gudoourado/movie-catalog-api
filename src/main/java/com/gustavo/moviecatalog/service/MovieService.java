package com.gustavo.moviecatalog.service;

import com.gustavo.moviecatalog.dto.MovieDTO;
import com.gustavo.moviecatalog.dto.ReviewDTO;
import com.gustavo.moviecatalog.exception.ResourceNotFoundException;
import com.gustavo.moviecatalog.model.Genre;
import com.gustavo.moviecatalog.model.Movie;
import com.gustavo.moviecatalog.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<MovieDTO> findAll() {
        return movieRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MovieDTO findById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com id: " + id));
        return toDTOWithReviews(movie);
    }

    public MovieDTO create(MovieDTO dto) {
        Movie movie = toEntity(dto);
        Movie saved = movieRepository.save(movie);
        return toDTO(saved);
    }

    public MovieDTO update(Long id, MovieDTO dto) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com id: " + id));

        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setDirector(dto.getDirector());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDurationMinutes(dto.getDurationMinutes());
        movie.setGenre(dto.getGenre());

        Movie updated = movieRepository.save(movie);
        return toDTO(updated);
    }

    public void delete(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Filme não encontrado com id: " + id);
        }
        movieRepository.deleteById(id);
    }

    public List<MovieDTO> findByGenre(Genre genre) {
        return movieRepository.findByGenre(genre).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MovieDTO> searchByKeyword(String keyword) {
        return movieRepository.searchByKeyword(keyword).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<MovieDTO> findTopRated() {
        return movieRepository.findTopRated().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private MovieDTO toDTO(Movie movie) {
        MovieDTO dto = new MovieDTO();
        dto.setId(movie.getId());
        dto.setTitle(movie.getTitle());
        dto.setDescription(movie.getDescription());
        dto.setDirector(movie.getDirector());
        dto.setReleaseYear(movie.getReleaseYear());
        dto.setDurationMinutes(movie.getDurationMinutes());
        dto.setGenre(movie.getGenre());
        dto.setAverageRating(movie.getAverageRating());
        dto.setTotalReviews(movie.getReviews() != null ? movie.getReviews().size() : 0);
        return dto;
    }

    private MovieDTO toDTOWithReviews(Movie movie) {
        MovieDTO dto = toDTO(movie);
        if (movie.getReviews() != null) {
            dto.setReviews(movie.getReviews().stream().map(r -> {
                ReviewDTO reviewDTO = new ReviewDTO();
                reviewDTO.setId(r.getId());
                reviewDTO.setAuthor(r.getAuthor());
                reviewDTO.setRating(r.getRating());
                reviewDTO.setComment(r.getComment());
                reviewDTO.setMovieId(movie.getId());
                return reviewDTO;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    private Movie toEntity(MovieDTO dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setDirector(dto.getDirector());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setDurationMinutes(dto.getDurationMinutes());
        movie.setGenre(dto.getGenre());
        return movie;
    }
}
