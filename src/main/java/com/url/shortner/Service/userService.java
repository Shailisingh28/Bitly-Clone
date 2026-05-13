package com.url.shortner.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.url.shortner.DTO.LoginRequest;
import com.url.shortner.Model.User;
import com.url.shortner.Repository.UserRepo;
import com.url.shortner.Security.JWTResponse;
import com.url.shortner.Security.JWTUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class userService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepo repo;
    private final AuthenticationManager manager;
    private final JWTUtil jwtUtil;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public JWTResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailImp userDetailImp = (UserDetailImp) authentication.getPrincipal();

        String jwt = jwtUtil.generateToken(userDetailImp);

        return new JWTResponse(jwt);
    }
}
