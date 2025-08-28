package com.example.demo.dto;

import java.time.LocalDateTime;

public class BookingResponse {
    private Long id;
    private Long userId;
    private String userName;
    private Long TheaterId;
    private String theatreName;
    private String MovieName;
    private Long showId;
    private LocalDateTime showTime;
    private String seatLabel;
    private double price;
    private LocalDateTime bookingTime;

    public BookingResponse(Long id, Long userId, String userName, Long theaterId,
                           String theatreName, String movieName, Long showId, LocalDateTime showTime,
                           String seatLabel, double price,LocalDateTime bookingTime) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.bookingTime=bookingTime;
        TheaterId = theaterId;
        this.theatreName = theatreName;
        MovieName = movieName;
        this.showId = showId;
        this.showTime = showTime;
        this.seatLabel = seatLabel;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTheaterId() {
        return TheaterId;
    }

    public void setTheaterId(Long theaterId) {
        TheaterId = theaterId;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public Long getShowId() {
        return showId;
    }

    public void setShowId(Long showId) {
        this.showId = showId;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    public String getSeatLabel() {
        return seatLabel;
    }

    public void setSeatLabel(String seatLabel) {
        this.seatLabel = seatLabel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getMovieTitle() {
        return this.MovieName;
    }
}
