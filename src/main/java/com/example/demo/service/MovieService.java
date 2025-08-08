package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepo;

    public Movie createMovie(String title, String genre, int duration){
        Movie movie=new Movie();
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setDuration(duration);
        return movieRepo.save(movie);
    }
    public List<Movie> getAllMovie(){
        return movieRepo.findAll();
    }
}
