package com.softxpert.taskManager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softxpert.taskManager.Entities.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, Long> {

}
