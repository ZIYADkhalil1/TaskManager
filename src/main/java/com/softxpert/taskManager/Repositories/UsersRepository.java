package com.softxpert.taskManager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softxpert.taskManager.Entities.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
