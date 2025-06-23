package com.example.moonrider.moonrider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.moonrider.moonrider.dto.IdentityRequest;
import com.example.moonrider.moonrider.dto.IdentityResponse;
import com.example.moonrider.moonrider.service.IdentityService;

@RestController
@RequestMapping("/identify")
public class IdentityController {

    @Autowired
    private IdentityService identityService;

    @PostMapping
    public ResponseEntity<IdentityResponse> identify(@RequestBody IdentityRequest request) {
        if (request.getEmail() == null && request.getPhoneNumber() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        IdentityResponse response = identityService.identify(request.getEmail(), request.getPhoneNumber());
        return ResponseEntity.ok(response);
    }
}

