package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
public class ShowSeatDTO {
    private Long id;
    private String seatLabel;
    private boolean booked;

    public ShowSeatDTO(Long id, String seatLabel, boolean booked) {
        this.id = id;
        this.seatLabel = seatLabel;
        this.booked = booked;
    }
}