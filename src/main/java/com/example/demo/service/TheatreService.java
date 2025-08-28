package com.example.demo.service;

import com.example.demo.dto.TheatreDTO;
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
        for (char c = 0; c < row; c++) {
            String rowLabel = getRowLabel(c);

            for (int i = 1; i <= number; i++) {
                TheatreSeat theatreSeat = new TheatreSeat(String.valueOf(rowLabel), String.valueOf(i));
                theatreSeat.setTheatre(theatre);
                theatreSeats.add(theatreSeat);
            }
        }

        // Save all seats in one go
        return theatreSeatRepo.saveAll(theatreSeats);
    }
    private String getRowLabel(int rowIndex) {
        StringBuilder label = new StringBuilder();

        while (rowIndex >= 0) {
            label.insert(0, (char) ('A' + (rowIndex % 26)));
            rowIndex = (rowIndex / 26) - 1;
        }

        return label.toString();
    }


    public List<TheatreDTO> getAllTheatre(){
        List<Theatre> theatres= theatreRepo.findAll();
        if(theatres.isEmpty()){
            return null;
        }
        return theatres.stream().map(t ->
                new TheatreDTO(
                        t.getId(),
                        t.getName(),
                        t.getLocation()
                )
        ).toList();
    }
    public TheatreDTO getTheatre(Long id){
        Theatre theatre=theatreRepo.findById(id).orElse(null);
        if(theatre==null) return null;
        return new TheatreDTO(
                theatre.getId(),
                theatre.getName(),
                theatre.getLocation()
        );
    }


}



