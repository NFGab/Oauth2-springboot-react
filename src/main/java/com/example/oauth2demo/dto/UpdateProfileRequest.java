package com.example.oauth2demo.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String name;
    private String bio;
    private String location;
}
