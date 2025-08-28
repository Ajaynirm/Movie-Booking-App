package com.example.demo.controller;

import com.example.demo.dto.TheatreDTO;
import com.example.demo.dto.TheatreRequest;
import com.example.demo.model.Booking;
import com.example.demo.model.Theatre;
import com.example.demo.repository.TheatreRepository;
import com.example.demo.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/theatre")
public class TheatreController {

    @Autowired
   private TheatreService theatreService;

    @GetMapping("/add-theatre")
    public ResponseEntity<?> bookTicket(@RequestParam String name,@RequestParam String location,@RequestParam String rows,@RequestParam String seatsPerRow) {
        System.out.println(name+" "+location+" "+rows+" "+seatsPerRow);
        Theatre saved = theatreService.addTheatre(name,location,Integer.parseInt(rows),Integer.parseInt(seatsPerRow));
        if(saved==null) return ResponseEntity.internalServerError().body("Failed ");
        return ResponseEntity.ok(new TheatreDTO(saved.getId(),saved.getName(),saved.getLocation()));
    }

    @GetMapping("get-all-theatre")
    public ResponseEntity<List<TheatreDTO>> getAllTheatre(){
        List<TheatreDTO> theatres=theatreService.getAllTheatre();
        return ResponseEntity.ok(theatres);
    }

    @GetMapping("get-theatre")
    public ResponseEntity<?> getTheatre(@RequestParam Long id){
        TheatreDTO theatre=theatreService.getTheatre(id);
        if(theatre==null){
            return ResponseEntity.badRequest().body("Invalid theatre id");
        }
        return ResponseEntity.ok(theatre);

    }



}





