package com.example.demo.controller;

import com.example.demo.dto.BookingRequest;
import com.example.demo.dto.BookingResponse;
import com.example.demo.model.Booking;
import com.example.demo.model.User;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<?> bookTicket(@RequestBody BookingRequest bookingRequest) {
        BookingResponse booking = bookingService.createBooking(bookingRequest.getShowId(),bookingRequest.getUserId(),bookingRequest.getSeatLabel());
        if(booking==null){
            return ResponseEntity.ok("Booking failed");
        }
        System.out.println("After" + booking);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/get-booking")
    public ResponseEntity<?> getBooking(@RequestParam Long id){
        BookingResponse booking=bookingService.getBooking(id);
        if(booking==null){
            return ResponseEntity.badRequest().body("Invalid booking id");
        }
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@PathVariable Long userId) {
        List<BookingResponse> bookings = bookingService.getUserBooking(userId);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/get-all-booking")
    public ResponseEntity<?> getAllBooking(){
        List<BookingResponse> bookingList=bookingService.getAll();
        if(bookingList.isEmpty()){
            return ResponseEntity.badRequest().body("No booking available");
        }
        return ResponseEntity.ok(bookingList);
    }
}


