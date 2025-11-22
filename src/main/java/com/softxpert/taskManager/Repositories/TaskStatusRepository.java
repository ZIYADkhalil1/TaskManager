package com.softxpert.taskManager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softxpert.taskManager.Entities.TaskStatus;

public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {

}
