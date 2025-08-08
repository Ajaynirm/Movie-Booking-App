package com.example.demo.controller;

import com.example.demo.dto.BookingRequest;
import com.example.demo.model.Booking;
import com.example.demo.model.User;
import com.example.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<Booking> bookTicket(@RequestBody BookingRequest bookingRequest) {
        Booking booking = bookingService.createBooking(bookingRequest.getShowId(),bookingRequest.getUserId(),bookingRequest.getSeatLabel());
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/get-booking")
    public ResponseEntity<?> getBooking(@RequestParam Long id){
        Booking booking=bookingService.getBooking(id);
        if(booking==null){
            return ResponseEntity.badRequest().body("Invalid booking id");
        }
        return ResponseEntity.ok(booking);
    }
    @GetMapping("/get-all-booking")
    public ResponseEntity<?> getAllBooking(){
        List<Booking> bookingList=bookingService.getAll();
        if(bookingList.isEmpty()){
            return ResponseEntity.badRequest().body("No booking available");
        }
        return ResponseEntity.ok(bookingList);
    }
}

