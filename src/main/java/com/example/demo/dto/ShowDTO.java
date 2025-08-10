package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ShowDTO {
    private Long id;
    private String movieTitle;
    private String theatreName;
    private String location;
    private LocalDateTime showTime;
    private List<ShowSeatDTO> showSeats;
}
