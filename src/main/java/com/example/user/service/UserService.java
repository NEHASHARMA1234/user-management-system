package com.example.user.service;

import com.example.user.model.UserDTO;
import com.example.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO saveUser(User user);
    Optional<UserDTO> getUserById(Long id);
    List<UserDTO> getAllUsers();
    void deleteUser(Long id);
    void updateUserDetails(Long id, UserDTO user);
    User findByUsername(String username);
    Optional<User> findByEmail(String email);
}
