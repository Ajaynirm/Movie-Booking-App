package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ShowSeatDTO {
    private Long id;
    private String seatLabel;
    private boolean booked;
}