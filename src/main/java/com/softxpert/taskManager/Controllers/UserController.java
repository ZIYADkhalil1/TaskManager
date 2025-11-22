package com.softxpert.taskManager.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softxpert.taskManager.DTOs.RegisterRequest;
import com.softxpert.taskManager.Services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_1')")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody RegisterRequest request) {
    try {
    	String message = userService.addUser(request);
    	return ResponseEntity.ok(message);
    } catch (IllegalArgumentException e) {
    	return ResponseEntity.badRequest().body(e.getMessage());
    	
    	}
    
    }
}
