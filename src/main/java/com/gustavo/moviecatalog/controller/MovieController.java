package com.gustavo.moviecatalog.controller;

import com.gustavo.moviecatalog.dto.MovieDTO;
import com.gustavo.moviecatalog.dto.ReviewDTO;
import com.gustavo.moviecatalog.model.Genre;
import com.gustavo.moviecatalog.service.MovieService;
import com.gustavo.moviecatalog.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;

    public MovieController(MovieService movieService, ReviewService reviewService) {
        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    // ===== ENDPOINTS DE FILMES =====

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        MovieDTO created = movieService.create(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id,
                                                 @Valid @RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok(movieService.update(id, movieDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MovieDTO>> getByGenre(@PathVariable Genre genre) {
        return ResponseEntity.ok(movieService.findByGenre(genre));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(movieService.searchByKeyword(keyword));
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<MovieDTO>> getTopRated() {
        return ResponseEntity.ok(movieService.findTopRated());
    }

    // ===== ENDPOINTS DE AVALIAÇÕES =====

    @PostMapping("/{movieId}/reviews")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable Long movieId,
                                                @Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO created = reviewService.addReview(movieId, reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{movieId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable Long movieId) {
        return ResponseEntity.ok(reviewService.getReviewsByMovie(movieId));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
