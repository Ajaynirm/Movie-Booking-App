package com.example.demo.service;

import com.example.demo.dto.BookingResponse;
import com.example.demo.dto.ShowSeatDTO;
import com.example.demo.model.Booking;
import com.example.demo.model.Show;
import com.example.demo.model.ShowSeat;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ShowRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // for sending WS messages

    @Autowired
    private ShowRepository showRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BookingRepository bookingRepo;


    public BookingResponse createBooking(Long showId, Long userId, String seatLabel) {
        Show show = showRepo.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));
        User user = userRepo.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        System.out.println(1);
        ShowSeat seat=show.findSeat(seatLabel);
        if(seat==null){
            throw new RuntimeException("Seat not available");
        }
        System.out.println(2);
        seat.setBooked(true);
        showRepo.save(show);
        System.out.println(3);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());
        booking.setShowSeat(seat);
        booking.setShow(show);
        messagingTemplate.convertAndSend(
                "/topic/show/" + showId,
                new ShowSeatDTO(seat.getId(), seat.getTheatreSeat().getSeatLabel(), seat.isBooked())
        );
        bookingRepo.save(booking);
        System.out.println(5);
        return new BookingResponse(booking.getId(),userId, user.getName(),show.getTheatre().getId(),show.getTheatre().getName(),show.getMovie().getTitle(),showId,show.getShowTime(),seatLabel,200,LocalDateTime.now());
    }

  public List<BookingResponse> getUserBooking(long userId){
        return bookingRepo.findByUserId(userId).stream().map(b->new BookingResponse(
                b.getId(),b.getUser().getId(),b.getUser().getName(),b.getShow().getTheatre().getId(),b.getShow().getTheatre().getName(),b.getShow().getMovie().getTitle(),b.getShow().getId(),b.getShow().getShowTime(),b.getShowSeat().getTheatreSeat().getSeatLabel(),200,b.getBookingTime()
        )).toList();
  }

   public List<BookingResponse> getAll(){
        List<Booking> bookingList= bookingRepo.findAll();
        return bookingList.stream().map(b-> new BookingResponse(
                b.getId(),b.getUser().getId(),b.getUser().getName(),b.getShow().getTheatre().getId(),b.getShow().getTheatre().getName(),b.getShow().getMovie().getTitle(),b.getShow().getId(),b.getShow().getShowTime(),b.getShowSeat().getTheatreSeat().getSeatLabel(),200,b.getBookingTime()
        )).toList();
   }
   public BookingResponse getBooking(Long id){
        Booking b= bookingRepo.findById(id).orElse(null);
        if(b==null){
            return null;
        }
        return new BookingResponse(
                b.getId(),b.getUser().getId(),b.getUser().getName(),b.getShow().getTheatre().getId(),b.getShow().getTheatre().getName(),b.getShow().getMovie().getTitle(),b.getShow().getId(),b.getShow().getShowTime(),b.getShowSeat().getTheatreSeat().getSeatLabel(),200,b.getBookingTime()
        );
   }


}




