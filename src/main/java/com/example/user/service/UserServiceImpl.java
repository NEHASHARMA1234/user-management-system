package com.example.user.service;


import com.example.user.model.UserDTO;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaUserProducer kafkaUserProducer;

    @Override
    public UserDTO saveUser(User user) {
        User savedUser = userRepository.save(user);
        String logMessage = "Registered: User registered: ID=" + savedUser.getId() + ", Email=" + savedUser.getEmail();
        // Kafka Event Publishing for audit/journaling
        kafkaUserProducer.publishUserEvent(logMessage);
        return convertUserIntoDTO(savedUser);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(this::convertUserIntoDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertUserIntoDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            userRepository.deleteById(id);

            String logMessage = "DELETE: User deleted: ID=" + user.getId() + ", Email=" + user.getEmail();
            // Kafka Event Publishing for audit/journaling
            kafkaUserProducer.publishUserEvent(logMessage);
        } else {
            throw new EntityNotFoundException("No user found with ID: " + id);
        }
    }


    @Override
    public void updateUserDetails(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        existingUser.setUsername(userDTO.getUsername());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(userDTO.getRole());

        userRepository.save(existingUser); // JPA will update since it's a managed entity

        String logMessage = "UPDATED: User updated: ID=" + existingUser.getId() + ", Email=" + userDTO.getEmail();
        // Kafka Event Publishing for audit/journaling
        kafkaUserProducer.publishUserEvent(logMessage);
    }


    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }


    public UserDTO convertUserIntoDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }

}
