package com.example.demo.service;

import com.example.demo.dto.ShowDTO;
import com.example.demo.dto.ShowDetailDTO;
import com.example.demo.dto.ShowRequest;
import com.example.demo.dto.ShowSeatDTO;
import com.example.demo.model.*;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ShowRepository;
import com.example.demo.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepo;
    @Autowired
    private TheatreRepository theatreRepo;
    @Autowired
    private MovieRepository movieRepo;


    public Show createShow(ShowRequest showRequest){
        Theatre theatre=theatreRepo.findById(showRequest.getTheatreId()).orElseThrow(()-> new RuntimeException("Invalid theatre"));
        Movie movie = movieRepo.findById(showRequest.getMovieId()).orElseThrow(()-> new RuntimeException("Invalid movie"));

        Show show=createShowWithSeats(theatre,movie,showRequest.getShowTime());
        return showRepo.save(show);
    }

    private Show createShowWithSeats(Theatre theatre,Movie movie, LocalDateTime showTime) {
        Show show = new Show();
        show.setTheatre(theatre);
        show.setMovie(movie);
        show.setShowTime(showTime);


        List<ShowSeat> showSeats = new ArrayList<>();
        for (TheatreSeat ts : theatre.getTheatreSeats()) {
            ShowSeat ss = new ShowSeat();
            ss.setShow(show);
            ss.setTheatreSeat(ts);
            ss.setBooked(false);
            showSeats.add(ss);
        }

        show.setShowSeats(showSeats);
        return showRepo.save(show); // cascade saves showSeats
    }

    public List<ShowDetailDTO> getAllShow() {
        List<Show> shows = showRepo.findAll();
        return shows.stream()
                .map(show -> new ShowDetailDTO(
                        show.getId(),
                        show.getMovie().getTitle(),
                        show.getTheatre().getName(),
                        show.getTheatre().getLocation(),
                        show.getShowTime()
                ))
                .toList();
    }


    public ShowDTO getShowDTO(long id){
        Show show = showRepo.findById(id).orElse(null);
        if (show == null) {
            return null;
        }

        return new ShowDTO(
                show.getId(),
                show.getMovie().getTitle(),
                show.getTheatre().getName(),
                show.getTheatre().getLocation(),
                show.getShowTime(),
                show.getShowSeats().stream()
                        .map(ss -> new ShowSeatDTO(
                                ss.getId(),
                                ss.getTheatreSeat().getSeatLabel(),
                                ss.isBooked()
                        ))
                        .toList()
        );
    }

//    public  boolean setLockSeat(long showId,long seatId,boolean lock){
//        Show show=showRepo.findById(showId).orElse(null);
//        if(show==null){
//            return false;
//        }
//
//        ShowSeat seat=show.getShowSeats().stream().filter(s-> s.getId()==seatId).findFirst().orElse(null);
//        if(seat==null){
//            return false;
//        }
//
//        seat.setLocked(lock);
//        showRepo.save(show);
//        return true;
//    }




}
