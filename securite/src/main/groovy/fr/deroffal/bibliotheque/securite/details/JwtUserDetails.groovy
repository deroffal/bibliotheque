package fr.deroffal.bibliotheque.securite.details


import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class JwtUserDetails implements UserDetails {

    final String username
    final String password

    JwtUserDetails(final String username, final String password) {
        this.username = username
        this.password = password
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        []
    }

    @Override
    boolean isAccountNonExpired() {
        true
    }

    @Override
    boolean isAccountNonLocked() {
        true
    }

    @Override
    boolean isCredentialsNonExpired() {
        true
    }

    @Override
    boolean isEnabled() {
        true
    }
}
