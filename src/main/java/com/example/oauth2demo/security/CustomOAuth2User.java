package com.example.oauth2demo.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User, OidcUser {

    private final OAuth2User oauth2User;
    private final Long userId;
    private final boolean isOidcUser;

    public CustomOAuth2User(OAuth2User oauth2User, Long userId) {
        this.oauth2User = oauth2User;
        this.userId = userId;
        this.isOidcUser = oauth2User instanceof OidcUser;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oauth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oauth2User.getName();
    }

    // OidcUser methods - only valid if wrapping an OidcUser
    @Override
    public Map<String, Object> getClaims() {
        if (isOidcUser) {
            return ((OidcUser) oauth2User).getClaims();
        }
        return getAttributes();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        if (isOidcUser) {
            return ((OidcUser) oauth2User).getUserInfo();
        }
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        if (isOidcUser) {
            return ((OidcUser) oauth2User).getIdToken();
        }
        return null;
    }
}