package com.gustavo.moviecatalog.service;

import com.gustavo.moviecatalog.dto.ReviewDTO;
import com.gustavo.moviecatalog.exception.ResourceNotFoundException;
import com.gustavo.moviecatalog.model.Movie;
import com.gustavo.moviecatalog.model.Review;
import com.gustavo.moviecatalog.repository.MovieRepository;
import com.gustavo.moviecatalog.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public ReviewService(ReviewRepository reviewRepository, MovieRepository movieRepository) {
        this.reviewRepository = reviewRepository;
        this.movieRepository = movieRepository;
    }

    public ReviewDTO addReview(Long movieId, ReviewDTO dto) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado com id: " + movieId));

        Review review = new Review();
        review.setAuthor(dto.getAuthor());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setMovie(movie);

        Review saved = reviewRepository.save(review);
        return toDTO(saved, movieId);
    }

    public List<ReviewDTO> getReviewsByMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new ResourceNotFoundException("Filme não encontrado com id: " + movieId);
        }
        return reviewRepository.findByMovieId(movieId).stream()
                .map(r -> toDTO(r, movieId))
                .collect(Collectors.toList());
    }

    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ResourceNotFoundException("Avaliação não encontrada com id: " + reviewId);
        }
        reviewRepository.deleteById(reviewId);
    }

    private ReviewDTO toDTO(Review review, Long movieId) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setAuthor(review.getAuthor());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setMovieId(movieId);
        return dto;
    }
}
