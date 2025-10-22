package com.example.oauth2demo.controller;

import com.example.oauth2demo.dto.UpdateProfileRequest;
import com.example.oauth2demo.dto.UserDTO;
import com.example.oauth2demo.security.CustomOAuth2User;
import com.example.oauth2demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated"));
        }

        CustomOAuth2User customUser = (CustomOAuth2User) principal;
        UserDTO user = userService.getUserById(customUser.getUserId());

        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", true);
        response.put("user", user);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/user/profile")
    public ResponseEntity<UserDTO> updateProfile(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestBody UpdateProfileRequest request) {

        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        CustomOAuth2User customUser = (CustomOAuth2User) principal;
        UserDTO updatedUser = userService.updateProfile(customUser.getUserId(), request);

        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/auth/status")
    public ResponseEntity<Map<String, Object>> checkAuthStatus(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", principal != null);

        if (principal != null) {
            CustomOAuth2User customUser = (CustomOAuth2User) principal;
            UserDTO user = userService.getUserById(customUser.getUserId());
            response.put("user", user);
        }

        return ResponseEntity.ok(response);
    }
}
