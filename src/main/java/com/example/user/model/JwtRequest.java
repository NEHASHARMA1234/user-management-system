package com.example.user.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {
    private String email;
    private String password;

    @Override
    public String toString() {
        return "JwtRequest{email='" + email + "', password='[PROTECTED]'}";
    }

}
