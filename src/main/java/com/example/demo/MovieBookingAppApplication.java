package com.example.demo;

import com.example.demo.model.Theatre;
import com.example.demo.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.demo.model")
public class MovieBookingAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(MovieBookingAppApplication.class, args);
	}
}



