package com.example.oauth2demo.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String displayName;
    private String bio;
}