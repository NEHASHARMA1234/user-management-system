package com.example.user.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDTO {
    private Long id;

    @NonNull
    private String username;

    @NonNull
    private String email;

    @NonNull
    private String role;

    // No password field here to prevent it from being sent in response
}