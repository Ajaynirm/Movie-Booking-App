package com.example.demo.controller;


import com.example.demo.config.JwtUtil;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.UserDetail;
import com.example.demo.dto.UserLoginDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User saved= userService.createUser(user.getName(),user.getEmail(),user.getPassword());
        return ResponseEntity.ok(saved);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest,  HttpServletResponse response){
//        User user=userService.login(loginRequest.getEmail(), loginRequest.getPassword());
//        //Generate jwt token
//        String jwt= jwtUtil.generateToken(user.getEmail());
//        //create cookie
//        Cookie cookie=new Cookie("token",jwt);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setMaxAge(7*24*60*60);
//        response.addCookie(cookie);
//        return ResponseEntity.ok("Login successful");
//
//    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response){

        Cookie cookie=new Cookie("token",null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // remove "Bearer "
        String userId = jwtUtil.extractUserId(token);
        String email = jwtUtil.extractEmail(token);
        String name= jwtUtil.extractName(token);

        UserDetail user = userService.getUser(email,name);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/createOrIgnore")
    public ResponseEntity<?> getUser(@RequestParam String name, String email){
        if(name==null || email==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing fields");
        }
        UserDetail user=userService.getUser(email,name);
        return ResponseEntity.ok(user);
    }
}




