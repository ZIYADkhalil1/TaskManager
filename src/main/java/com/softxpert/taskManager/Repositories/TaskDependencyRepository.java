package com.softxpert.taskManager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softxpert.taskManager.Entities.TaskDependency;

public interface TaskDependencyRepository extends JpaRepository<TaskDependency, Long> {

}
