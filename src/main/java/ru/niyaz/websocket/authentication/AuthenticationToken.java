package ru.niyaz.websocket.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.niyaz.websocket.dto.UserInfo;

import java.util.Collection;

/**
 * Created by user on 23.04.2016.
 */
public class AuthenticationToken implements Authentication {

    private Long userID;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isAuthenticated;
    private UserInfo principal;

    public AuthenticationToken(Long userID, UserInfo principal, Collection<? extends GrantedAuthority> authorities) {
        this.userID = userID;
        this.principal = principal;
        this.authorities = authorities;
        this.isAuthenticated = true;
    }

    public Long getUserID() {
        return userID;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Object getCredentials() {
        return null;
    }

    public Object getDetails() {
        return null;
    }

    public String getName() {
        UserInfo userInfo = (UserInfo) principal;
        if (userInfo != null)
            return userInfo.getName() + " " + userInfo.getLastName();
        else
            return null;
    }

    public Object getPrincipal() {
        return principal;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        isAuthenticated = b;
    }

}
