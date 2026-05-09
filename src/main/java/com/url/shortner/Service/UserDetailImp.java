package com.url.shortner.Service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.url.shortner.Model.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Service
@Data
@NoArgsConstructor
public class UserDetailImp implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long userid;
    private String email;
    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailImp(Long userid, String email, String password, String username,
            Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.userid = userid;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserDetailImp build(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
        return new UserDetailImp(user.getUserid(), user.getEmail(), user.getPassword(), user.getUsername(),
                Collections.singleton(authority));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override

    public String getPassword() {
        return password;
    }

    @Override

    public String getUsername() {
        return username;
    }

}
