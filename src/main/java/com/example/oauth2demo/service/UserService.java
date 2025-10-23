package com.example.oauth2demo.service;

import com.example.oauth2demo.dto.UpdateProfileRequest;
import com.example.oauth2demo.dto.UserDTO;
import com.example.oauth2demo.entity.AuthProvider;
import com.example.oauth2demo.entity.User;
import com.example.oauth2demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    @Transactional
    public UserDTO updateProfile(Long id, UpdateProfileRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getDisplayName() != null) {
            user.setDisplayName(request.getDisplayName());
        }
        if (request.getBio() != null) {
            user.setBio(request.getBio());
        }

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setDisplayName(user.getDisplayName());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setBio(user.getBio());
        dto.setAuthProviders(
                user.getAuthProviders().stream()
                        .map(ap -> new UserDTO.AuthProviderDTO(
                                ap.getId(),
                                ap.getProvider().name(),
                                ap.getProviderEmail()
                        ))
                        .collect(Collectors.toList())
        );
        return dto;
    }
}