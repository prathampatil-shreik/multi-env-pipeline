package com.example.taskflow.controller;

import com.example.taskflow.config.ApplicationConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApplicationController {

    private final ApplicationConfig config;

    public ApplicationController(ApplicationConfig config) {
        this.config = config;
    }

    @GetMapping("/api/info")
    public ResponseEntity<Map<String, String>> info() {
        return ResponseEntity.ok(Map.of(
                "application", "TaskFlow - Task Management System",
                "environment", config.getEnvironment(),
                "version", config.getVersion(),
                "message", config.getMessage()
        ));
    }

    @GetMapping("/version")
    public ResponseEntity<Map<String, String>> version() {
        return ResponseEntity.ok(Map.of(
                "application", "TaskFlow",
                "environment", config.getEnvironment(),
                "version", config.getVersion()
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }
}
