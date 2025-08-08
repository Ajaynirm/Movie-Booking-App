package com.example.demo.service;

import com.example.demo.model.Booking;
import com.example.demo.model.Show;
import com.example.demo.model.ShowSeat;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ShowRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private ShowRepository showRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Transactional
    public Booking createBooking(Long showId, Long userId, String seatLabel) {
        Show show = showRepo.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));
        User user = userRepo.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));

        ShowSeat seat=show.findSeat(seatLabel);
        if(seat==null){
            throw new RuntimeException("Seat not available");
        }

        seat.setBooked(true);
        showRepo.save(show);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());
        booking.setShowSeat(seat);
        booking.setShow(show);
        return bookingRepo.save(booking);

    }
   public List<Booking> getAll(){
        return bookingRepo.findAll();
   }
   public Booking getBooking(Long id){
        return bookingRepo.findById(id).orElse(null);
   }
}




