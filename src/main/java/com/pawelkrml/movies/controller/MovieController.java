package com.pawelkrml.movies.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pawelkrml.movies.model.Movie;
import com.pawelkrml.movies.service.MovieService;

@RestController
@RequestMapping("/v1/movies")
public class MovieController {

  @Autowired
  private MovieService movieService;

  @GetMapping
  public Iterable<Movie> getAllMovies() {
    return movieService.getAllMovies();
  }

  @PostMapping
  public Movie createMovie(@RequestBody Movie movie) {
    return movieService.saveMovie(movie);
  }

  @GetMapping("/{id}")
  public Movie getMovieById(@PathVariable String id) {
    Optional<Movie> movie = movieService.getMovieById(UUID.fromString(id));

    if (movie.isPresent()) {
      return movie.get();
    }

    return null;
  }

  @DeleteMapping("/{id}")
  public boolean deleteMovie(@PathVariable UUID id) {
    movieService.deleteMovieById(id);

    return true;
  }
}
