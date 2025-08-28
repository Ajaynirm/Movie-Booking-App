package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.dto.UserDetail;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // initialize mocks
    }

    // ----------- CREATE USER TESTS -----------

    @Test
    void createUser_success() {
        String name = "Alice";
        String email = "alice@example.com";
        String password = "password123";

        // Mock behavior
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(password)).thenReturn("hashedPassword");
        User savedUser = new User(name, email, "hashedPassword");
        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = userService.createUser(name, email, password);

        // Assert

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        assertEquals("hashedPassword", result.getPassword());
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void createUser_fail_duplicateEmail() {
        String email = "bob@example.com";
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () ->
                userService.createUser("Bob", email, "password")
        );
    }

    // ----------- LOGIN TESTS -----------

    @Test
    void login_success() {
        String email = "carol@example.com";
        String password = "pass123";
        User user = new User("Carol", email, "hashedPass");

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        UserDetail detail = userService.login(email, password);

        assertEquals(email, detail.getEmail());
        assertEquals("Carol", detail.getName());
    }

    @Test
    void login_fail_invalidEmail() {
        when(userRepo.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                userService.login("nonexistent@example.com", "anyPass")
        );
    }

    @Test
    void login_fail_invalidPassword() {
        String email = "dave@example.com";
        User user = new User("Dave", email, "hashedPwd");

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPass", user.getPassword())).thenReturn(false);

        assertThrows(RuntimeException.class, () ->
                userService.login(email, "wrongPass")
        );
    }

    // ----------- GET USER TESTS -----------

    @Test
    void getUser_existingUser() {
        String name = "Eve";
        String email = "eve@example.com";
        User user = new User(name, email, "hashedPwd");
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        UserDetail detail = userService.getUser(email, name);

        assertEquals(name, detail.getName());
        assertEquals(email, detail.getEmail());
        verify(userRepo, never()).save(any());
    }

    @Test
    void getUser_newUser() {
        String name = "Frank";
        String email = "frank@example.com";
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        User newUser = new User(name, email, null);
        when(userRepo.save(any(User.class))).thenReturn(newUser);

        UserDetail detail = userService.getUser(email, name);

        assertEquals(name, detail.getName());
        assertEquals(email, detail.getEmail());
        verify(userRepo, times(1)).save(any(User.class));
    }
}
