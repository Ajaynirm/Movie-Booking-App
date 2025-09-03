package com.example.demo.controller;


import com.example.demo.config.JwtDecoder;
import com.example.demo.dto.UserDetail;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtDecoder jwtDecoder;


    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User saved= userService.createUser(user.getName(),user.getEmail(),user.getPassword());
        return ResponseEntity.ok(saved);
    }


    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuth(@RequestHeader("Authorization") String authHeader) throws Exception {
        String token = authHeader.substring(7);
        String email = jwtDecoder.decodeJwt(token,"email");
        String name= jwtDecoder.decodeJwt(token,"name");
        if(email==null || name==null ||email.isEmpty() || name.isEmpty()){
            return ResponseEntity.ok(null);
        }
        UserDetail user = userService.getUser(email,name);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String authHeader) throws Exception {
        String token = authHeader.substring(7); // remove "Bearer "
        String email = jwtDecoder.decodeJwt(token,"email");
        String name= jwtDecoder.decodeJwt(token,"name");
        UserDetail user = userService.getUser(email,name);
        System.out.println(user+"111--------------------");
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




