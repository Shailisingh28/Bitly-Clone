package com.url.shortner.DTO;

import java.util.Set;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
