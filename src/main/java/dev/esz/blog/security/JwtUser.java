package dev.esz.blog.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class JwtUser implements UserDetails {
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
    private Long id;

    private String username;
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter

    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean enabled;
    @Getter(onMethod = @__(@JsonIgnore))
    private final Date lastPasswordResetDate;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
