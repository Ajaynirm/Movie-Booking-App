package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TheatreRequest {
    private String name;
    private String location;
    private int row;
    private int number;

    public TheatreRequest(String name, String location, int row, int number) {
        this.name = name;
        this.location = location;
        this.row = row;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }
}
