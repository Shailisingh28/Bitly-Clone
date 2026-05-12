package com.url.shortner.DTO;

import java.util.Set;

import lombok.Data;

@Data
public class Registerrequest {
    private String username;
    private String email;
    private Set<String> roles;
    private String password;
}
