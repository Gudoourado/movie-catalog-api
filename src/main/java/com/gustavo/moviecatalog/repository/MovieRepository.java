package com.gustavo.moviecatalog.repository;

import com.gustavo.moviecatalog.model.Genre;
import com.gustavo.moviecatalog.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenre(Genre genre);

    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByDirectorContainingIgnoreCase(String director);

    List<Movie> findByReleaseYear(Integer year);

    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(m.director) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(m.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Movie> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT m FROM Movie m LEFT JOIN m.reviews r GROUP BY m ORDER BY AVG(r.rating) DESC")
    List<Movie> findTopRated();
}
