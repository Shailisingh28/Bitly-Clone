package com.url.shortner.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.url.shortner.Model.User;
import com.url.shortner.Repository.UserRepo;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    UserRepo UserRepo;
    UserDetailImp userDetailImp;

    public UserDetailsServiceImp(UserRepo UserRepo, UserDetailImp userDetailImp) {
        this.UserRepo = UserRepo;
        this.userDetailImp = userDetailImp;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found " + username));
        return userDetailImp.build(user);
    }

}
