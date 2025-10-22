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

        Long userId = extractUserId(principal);
        UserDTO user = userService.getUserById(userId);

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

        Long userId = extractUserId(principal);
        UserDTO updatedUser = userService.updateProfile(userId, request);

        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/auth/status")
    public ResponseEntity<Map<String, Object>> checkAuthStatus(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", principal != null);

        if (principal != null) {
            Long userId = extractUserId(principal);
            UserDTO user = userService.getUserById(userId);
            response.put("user", user);
        }

        return ResponseEntity.ok(response);
    }

    private Long extractUserId(OAuth2User principal) {
        if (principal instanceof CustomOAuth2User) {
            return ((CustomOAuth2User) principal).getUserId();
        }
        throw new IllegalStateException("Unexpected OAuth2User type: " + principal.getClass().getName());
    }
}