package com.softxpert.taskManager.Services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.softxpert.taskManager.DTOs.RegisterRequest;
import com.softxpert.taskManager.Entities.User;
import com.softxpert.taskManager.Repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Registers a new user in the system.
     * Validates uniqueness of email, encodes password, and saves the user.
     *
     * @param newUser the registration request containing user details
     * @return a status message indicating success or failure
     */
	public String addUser(RegisterRequest newUser) {
		if(userRepository.findByEmail(newUser.email()).isPresent()){
			return("User Already Exists");
		}
		
		User user = new User();
        user.setFirstName(newUser.firstName());
        user.setLastName(newUser.lastName());
        user.setEmail(newUser.email());
        user.setPassword(passwordEncoder.encode(newUser.password()));
        user.setRole(newUser.roleId());

        userRepository.save(user);
        
        return("User Added Succesfully");

	}
}
