package edu.filhan.tp.models.security;

import edu.filhan.tp.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails {

    private User user;

    public AppUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole().getId() == 1) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if (user.getRole().getId() == 2) {
            return List.of(new SimpleGrantedAuthority("ROLE_CHEF"));
        } else if (user.getRole().getId() == 3) {
            return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
        } else if (user.getRole().getId() == 4) {
            return List.of(new SimpleGrantedAuthority("ROLE_OUVRIER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPseudo();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
