package com.softxpert.taskManager.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softxpert.taskManager.DTOs.RegisterRequest;
import com.softxpert.taskManager.Entities.User;
import com.softxpert.taskManager.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_1')")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody RegisterRequest request) {

        // Prevent duplicate emails
        if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists!");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password())); // 🔐 Encrypt password
        user.setRole(request.roleId()); // using integer roles

        userRepository.save(user);

        return ResponseEntity.ok("User created successfully");
    }
}
