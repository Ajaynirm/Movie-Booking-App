package com.example.demo.service;

import com.example.demo.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;

import static org.mockito.Mockito.when;


import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepo;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ----------- CREATE MOVIE TESTS -----------

    @Test
    void createMovie_success_newMovie() {
        String title = "Coolie";
        String genre = "Sci-Fi";
        int duration = 148;

        // Mock: no existing movie with this title
        when(movieRepo.findByTitle(title)).thenReturn(null);

        Movie savedMovie = new Movie();
        savedMovie.setTitle(title);
        savedMovie.setGenre(genre);
        savedMovie.setDuration(duration);

        when(movieRepo.save(any(Movie.class))).thenReturn(savedMovie);

        // Act
        Movie result = movieService.createMovie(title, genre, duration);

        // Assert
        assertNotNull(result);
        assertEquals(title, result.getTitle());
        assertEquals(genre, result.getGenre());
        assertEquals(duration, result.getDuration());
        verify(movieRepo, times(1)).save(any(Movie.class));
    }

    @Test
    void createMovie_alreadyExists() {
        String title = "Titanic";
        Movie existing = new Movie();
        existing.setTitle(title);

        when(movieRepo.findByTitle(title)).thenReturn(existing);

        Movie result = movieService.createMovie(title, "Romance", 195);

        // If movie already exists, should return existing and not save again
        assertEquals(existing, result);
        verify(movieRepo, never()).save(any(Movie.class));
    }

    // ----------- GET ALL MOVIES TEST -----------

    @Test
    void getAllMovie_success() {
        Movie m1 = new Movie(); m1.setTitle("Movie1");
        Movie m2 = new Movie(); m2.setTitle("Movie2");

        when(movieRepo.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Movie> movies = movieService.getAllMovie();

        assertEquals(2, movies.size());
        verify(movieRepo, times(1)).findAll();
    }

    // ----------- GET MOVIE BY ID TEST -----------

    @Test
    void getMovie_found() {
        Movie movie = new Movie();
        movie.setTitle("Avengers");
        movie.setId(1L);

        when(movieRepo.findById(1L)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovie(1L);

        assertNotNull(result);
        assertEquals("Avengers", result.getTitle());
    }

    @Test
    void getMovie_notFound() {
        when(movieRepo.findById(2L)).thenReturn(Optional.empty());

        Movie result = movieService.getMovie(2L);

        assertNull(result);
    }
}



