package com.felixramos.album_management_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public ResponseEntity<String> landing() {
        return ResponseEntity.ok(
                "Album Management API is running. " +
                        "Use /health or /api/** endpoints."
        );
    }
}
