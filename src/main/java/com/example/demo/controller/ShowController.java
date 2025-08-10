package com.example.demo.controller;

import com.example.demo.dto.ShowDTO;
import com.example.demo.dto.ShowRequest;
import com.example.demo.model.Show;
import com.example.demo.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
            List<ShowDTO> shows=showService.getAllShow();
            if(shows.isEmpty()){
                return ResponseEntity.badRequest().body("No show available");
            }
            return ResponseEntity.ok(shows);
        }

        @GetMapping("/get-show")
        public ResponseEntity<?> getShow(long showId){
            ShowDTO show=showService.getShow(showId);
            if(show==null){
                return ResponseEntity.badRequest().body("Invalid id");
            }

            return ResponseEntity.ok(show);

        }

    }




