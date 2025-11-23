package com.softxpert.taskManager.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softxpert.taskManager.Entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByAssignee(Integer assignee);
}
