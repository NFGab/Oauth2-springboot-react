package com.example.oauth2demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String displayName;
    private String avatarUrl;
    private String bio;
    private List<AuthProviderDTO> authProviders;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthProviderDTO {
        private Long id;
        private String provider;
        private String providerEmail;
    }
}