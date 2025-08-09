package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

//import java.util.List;


@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email cannot blank")
    @Email(message = "invalid email ")
    String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password;

//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    private final List<Booking> bookings=new ArrayList<>();

    public User() {

    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public List<Booking> getBookings() {
//        return bookings;
//    }
//
//    public void addBookings(Booking booking) {
//        this.bookings.add(booking);
//    }
}
