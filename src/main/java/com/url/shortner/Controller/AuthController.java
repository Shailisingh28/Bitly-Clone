package com.url.shortner.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortner.DTO.LoginRequest;
import com.url.shortner.DTO.Registerrequest;
import com.url.shortner.Model.User;
import com.url.shortner.Service.userService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    userService service;

    @PostMapping("/public/register")
    public ResponseEntity<?> registerUser(@RequestBody Registerrequest request) {
        User user = new User();
        user.setPassword(request.getPassword());
        user.setRole("ROLE_USER");
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        service.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/public/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginrequest) {
        return ResponseEntity.ok(service.loginUser(loginrequest));
    }

}
