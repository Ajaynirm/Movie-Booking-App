package com.example.demo.dto;

public class UserDetail {
    private long id;
    private String name;
    private String email;

    public UserDetail() {
    }

    public UserDetail(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getId() {
        return id;
    }
}
