package com.softxpert.taskManager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softxpert.taskManager.Entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
