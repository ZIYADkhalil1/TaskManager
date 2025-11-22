package com.softxpert.taskManager.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class Users {
	@Id
	private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    
    private Integer role;
    
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;

}
