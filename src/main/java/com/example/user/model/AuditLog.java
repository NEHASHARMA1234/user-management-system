package com.example.user.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "audit")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;         // e.g. "LOGIN", "REGISTER", "DELETE"
    private String email;           // who performed it
    private LocalDateTime timestamp;
    private String details;        // optional details (e.g., "User deleted", etc.)

    public AuditLog(String message) {
    }
}

