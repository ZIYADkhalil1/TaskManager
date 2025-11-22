package com.softxpert.taskManager.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softxpert.taskManager.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	 Optional<User> findByEmail(String email);
	 
}
