package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    private Long showId;
    private Long userId;
    private String seatLabel;

    public Long getShowId() {
        return showId;
    }

    public Long getUserId() {
        return userId;
    }

    public String  getSeatLabel() {
        return seatLabel;
    }
}
