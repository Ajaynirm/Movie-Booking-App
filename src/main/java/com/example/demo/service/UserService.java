package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder  passwordEncoder;


    public User createUser(String name, String email, String password){
        User user=userRepo.findByEmail(email).orElse(null);
        if(user!=null) throw  new RuntimeException("Email already exists");
        String hashedPwd=passwordEncoder.encode(password);
        User saved=new User(name,email,hashedPwd);
       return userRepo.save(saved);
    }

    public User login(String email, String password) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }




}
