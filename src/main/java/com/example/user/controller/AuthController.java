package com.example.user.controller;

import com.example.user.model.JwtRequest;
import com.example.user.model.JwtResponse;
import com.example.user.service.JwtUtils;
import com.example.user.service.KafkaUserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private KafkaUserProducer kafkaUserProducer;

    @PostMapping("/login")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        final String token = jwtUtil.generateToken(userDetails.getUsername());
        String logMessage = "LOGGED-IN: User logged in: Email=" + jwtRequest.getEmail();
        // Kafka Event Publishing for audit/journaling
        kafkaUserProducer.publishUserEvent(logMessage);
        return new JwtResponse(token);
    }
}

