package com.example.demo.service;

import com.example.demo.dto.BookingResponse;
import com.example.demo.dto.ShowSeatDTO;
import com.example.demo.model.*;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.ShowRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private ShowRepository showRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private BookingRepository bookingRepo;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ----------- CREATE BOOKING TESTS -----------

    @Test
    void createBooking_success() {
        Long showId = 1L;
        Long userId = 2L;
        String seatLabel = "A1";

        // Mock Show
        TheatreSeat theatreSeat = new TheatreSeat();
        theatreSeat.setSeatLabel(seatLabel);

        ShowSeat showSeat = new ShowSeat();
        showSeat.setTheatreSeat(theatreSeat);
        showSeat.setBooked(false);

        Show show = new Show();
        show.setId(showId);
        show.setMovie(new Movie("Inception","sci-fi",145));
        Theatre theatre = new Theatre();
        theatre.setId(10L);
        theatre.setName("PVR");
        show.setTheatre(theatre);
        show.setShowTime(LocalDateTime.now());
        show.setSeats(Arrays.asList(showSeat));

        when(showRepo.findById(showId)).thenReturn(Optional.of(show));

        // Mock User
        User user = new User();
        user.setId(userId);
        user.setName("Alice");
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        // Mock BookingRepository
        Booking booking = new Booking();
        booking.setId(100L);
        booking.setUser(user);
        booking.setShow(show);
        booking.setShowSeat(showSeat);
        booking.setBookingTime(LocalDateTime.now());
        when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

        // Act
        BookingResponse response = bookingService.createBooking(showId, userId, seatLabel);

        // Assert
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(seatLabel, response.getSeatLabel());
        verify(showRepo, times(1)).save(any(Show.class));
        verify(bookingRepo, times(1)).save(any(Booking.class));
        verify(messagingTemplate, times(1)).convertAndSend(anyString(), any(ShowSeatDTO.class));
    }

    @Test
    void createBooking_showNotFound() {
        when(showRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookingService.createBooking(1L, 1L, "A1"));
    }

    @Test
    void createBooking_userNotFound() {
        Show show = new Show();
        when(showRepo.findById(1L)).thenReturn(Optional.of(show));
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookingService.createBooking(1L, 1L, "A1"));
    }

    @Test
    void createBooking_seatNotAvailable() {
        Show show = new Show(); // show with no seats
        when(showRepo.findById(1L)).thenReturn(Optional.of(show));
        User user = new User();
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> bookingService.createBooking(1L, 1L, "A1"));
    }

    // ----------- GET USER BOOKINGS -----------

    @Test
    void getUserBooking_success() {
        User user = new User();
        user.setId(1L);
        user.setName("Bob");

        Show show = new Show();
        show.setId(10L);
        Movie movie=new Movie();
        movie.setTitle("Matrix");
        movie.setGenre("Action");
        movie.setDuration(145);
        show.setMovie(movie);


        Theatre theatre = new Theatre();
        theatre.setId(20L);
        theatre.setName("INOX");
        show.setTheatre(theatre);
        show.setShowTime(LocalDateTime.now());

        TheatreSeat theatreSeat = new TheatreSeat();
        theatreSeat.setSeatLabel("B2");
        ShowSeat showSeat = new ShowSeat();
        showSeat.setTheatreSeat(theatreSeat);

        Booking booking = new Booking();
        booking.setId(100L);
        booking.setUser(user);
        booking.setShow(show);
        booking.setShowSeat(showSeat);
        booking.setBookingTime(LocalDateTime.now());

        when(bookingRepo.findByUserId(1L)).thenReturn(Arrays.asList(booking));

        List<BookingResponse> bookings = bookingService.getUserBooking(1L);
        assertEquals(1, bookings.size());
        assertEquals("Matrix", bookings.get(0).getMovieTitle());
    }

    // ----------- GET ALL BOOKINGS -----------

    @Test
    void getAll_success() {
        User user = new User();
        user.setId(1L);
        user.setName("Bob");

        Show show = new Show();
        show.setId(10L);
        Movie movie=new Movie();
        movie.setTitle("Matrix");
        movie.setGenre("Action");
        movie.setDuration(145);
        show.setMovie(movie);


        Theatre theatre = new Theatre();
        theatre.setId(20L);
        theatre.setName("INOX");
        show.setTheatre(theatre);
        show.setShowTime(LocalDateTime.now());

        TheatreSeat theatreSeat = new TheatreSeat();
        theatreSeat.setSeatLabel("B2");
        ShowSeat showSeat = new ShowSeat();
        showSeat.setTheatreSeat(theatreSeat);

        Booking booking = new Booking();
        booking.setId(100L);
        booking.setUser(user);
        booking.setShow(show);
        booking.setShowSeat(showSeat);
        booking.setBookingTime(LocalDateTime.now());


        when(bookingRepo.findAll()).thenReturn(Arrays.asList(booking));

        List<BookingResponse> allBookings = bookingService.getAll();
        assertEquals(1, allBookings.size());
    }

    // ----------- GET BOOKING BY ID -----------

    @Test
    void getBooking_found() {
        User user = new User();
        user.setId(1L);
        user.setName("Bob");

        Show show = new Show();
        show.setId(10L);
        Movie movie=new Movie();
        movie.setTitle("Matrix");
        movie.setGenre("Action");
        movie.setDuration(145);
        show.setMovie(movie);


        Theatre theatre = new Theatre();
        theatre.setId(20L);
        theatre.setName("INOX");
        show.setTheatre(theatre);
        show.setShowTime(LocalDateTime.now());

        TheatreSeat theatreSeat = new TheatreSeat();
        theatreSeat.setSeatLabel("B2");
        ShowSeat showSeat = new ShowSeat();
        showSeat.setTheatreSeat(theatreSeat);

        Booking booking = new Booking();
        booking.setId(100L);
        booking.setUser(user);
        booking.setShow(show);
        booking.setShowSeat(showSeat);
        booking.setBookingTime(LocalDateTime.now());

        when(bookingRepo.findById(100L)).thenReturn(Optional.of(booking));

        BookingResponse response = bookingService.getBooking(booking.getId());
        assertNotNull(response);
        assertEquals(booking.getId(), response.getId());
    }

    @Test
    void getBooking_notFound() {
        when(bookingRepo.findById(1L)).thenReturn(Optional.empty());

        BookingResponse response = bookingService.getBooking(1L);
        assertNull(response);
    }
}

