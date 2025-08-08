package com.example.demo.controller;

import com.example.demo.dto.ShowRequest;
import com.example.demo.model.Movie;
import com.example.demo.model.Show;
import com.example.demo.service.MovieService;
import com.example.demo.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/show")
public class ShowController {

        @Autowired
        private ShowService showService;

        @PostMapping("/add-show")
        public ResponseEntity<Show> addMovie(@RequestBody ShowRequest showRequest){
            Show saved= showService.createShow(showRequest);
            return ResponseEntity.ok(saved);
        }



        @GetMapping("/get-all-show")
        public ResponseEntity<?> getAllShow(){
            List<Show> shows=showService.getAllShow();
            return ResponseEntity.ok(shows);
        }

        @GetMapping("/get-show")
        public ResponseEntity<Show> getShow(long showId){
            Show show=showService.getShow(showId);
            return ResponseEntity.ok(show);
        }

    }



