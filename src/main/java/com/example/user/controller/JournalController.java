package com.example.user.controller;

import com.example.user.model.AuditLog;
import com.example.user.repository.AuditLogRepository;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    UserService userService;

    @Autowired
    AuditLogRepository auditLogRepository;

    @GetMapping("/all-audit-logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AuditLog>> getAuditLogs() {
        return ResponseEntity.ok(auditLogRepository.findAll());
    }

    @GetMapping("/audit-logs/{id}")
    public ResponseEntity<Optional<AuditLog>> getEventsByUserId(@PathVariable(value = "id") Long name) {
        return ResponseEntity.ok(auditLogRepository.findById(name));
    }

    @GetMapping("/audit-logs")
    public ResponseEntity<Optional<AuditLog>> getEventsByUserEmail(@RequestParam(value = "email") String email) {
        return ResponseEntity.ok(auditLogRepository.findByEmail(email));
    }
}
