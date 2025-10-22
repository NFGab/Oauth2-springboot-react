package com.example.oauth2demo.security;

import com.example.oauth2demo.entity.User;
import com.example.oauth2demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oauth2User.getAttributes();

        User user = processOAuth2User(registrationId, attributes);

        return new CustomOAuth2User(oauth2User, user.getId());
    }

    private User processOAuth2User(String provider, Map<String, Object> attributes) {
        String providerId;
        String email;
        String name;
        String avatarUrl;

        if ("google".equals(provider)) {
            providerId = (String) attributes.get("sub");
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            avatarUrl = (String) attributes.get("picture");
        } else if ("github".equals(provider)) {
            providerId = String.valueOf(attributes.get("id"));
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            avatarUrl = (String) attributes.get("avatar_url");

            // GitHub might not return email in attributes if it's private
            if (email == null) {
                email = (String) attributes.get("login") + "@github.local";
            }
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
        }

        // Check if user exists
        String finalEmail = email;
        return userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> {
                    // Create new user
                    User newUser = new User();
                    newUser.setEmail(finalEmail);
                    newUser.setName(name);
                    newUser.setAvatarUrl(avatarUrl);
                    newUser.setProvider(provider);
                    newUser.setProviderId(providerId);
                    return userRepository.save(newUser);
                });
    }
}
