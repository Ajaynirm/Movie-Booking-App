package com.example.demo.service;

import com.example.demo.model.Theatre;
import com.example.demo.model.TheatreSeat;
import com.example.demo.repository.TheatreRepository;
import com.example.demo.repository.TheatreSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepo;

    @Autowired
    private TheatreSeatRepository theatreSeatRepo;

    public Theatre addTheatre(String name, String location, int row, int number) {
        Theatre theatre = new Theatre();
        theatre.setName(name);
        theatre.setLocation(location);

        // Step 1: Save the theatre first
        Theatre savedTheatre = theatreRepo.save(theatre);

        // Step 2: Now create TheatreSeats with the saved theatre reference
        List<TheatreSeat> theatreSeats = makeTheatreSeats(row, number, savedTheatre);

        savedTheatre.setTheatreSeats(theatreSeats);

        return theatreRepo.save(savedTheatre); // optional second save to persist the seat list mapping
    }

    private List<TheatreSeat> makeTheatreSeats(int row, int number, Theatre theatre) {
        List<TheatreSeat> theatreSeats = new ArrayList<>();
        for (char c = 'A'; c < 'A' + row; c++) {
            for (int i = 1; i <= number; i++) {
                TheatreSeat theatreSeat = new TheatreSeat(String.valueOf(c), String.valueOf(i));
                theatreSeat.setTheatre(theatre);
                theatreSeats.add(theatreSeat);
            }
        }

        // Save all seats in one go
        return theatreSeatRepo.saveAll(theatreSeats);
    }

    public List<Theatre> getAllTheatre(){
        return theatreRepo.findAll();
    }
    public Theatre getTheatre(Long id){
        return theatreRepo.findById(id).orElse(null);
    }


}
