package com.example.demo.controller;



import com.example.demo.model.Movie;
import com.example.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/add-movie")
    public ResponseEntity<?> addMovie( @RequestParam String title, @RequestParam String genre, @RequestParam int duration){
       Movie saved= movieService.createMovie(title,genre,duration);
       return ResponseEntity.ok(saved);
    }



    @GetMapping("/get-all-movie")
    public ResponseEntity<?> getAllMovie(){
        List<Movie> movies=movieService.getAllMovie();
        return ResponseEntity.ok(movies);
    }
}



