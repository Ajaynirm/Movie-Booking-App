package com.example.demo.dto;

import lombok.*;


@Getter
@Setter
public class TheatreDTO {
    private Long id;

    private String name;

    private String location;

    public TheatreDTO(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}




